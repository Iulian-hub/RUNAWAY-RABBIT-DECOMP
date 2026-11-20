# Audit aprofundat: conținut, arme, progresie și persistență

**Repository analizat:** `Iulian-hub/RUNAWAY-RABBIT-DECOMP`, ramura `main`  
**Tip analiză:** statică, pe sursele Blitz3D decompilate/modificate. Nu s-au executat teste de gameplay și nu s-a comparat comportamentul cu executabilul original Mighty Rodent.  
**Regulă de lucru:** documentație-only; codul sursă al jocului nu a fost modificat.

Acest document continuă `DEEP_AUDIT_GAMEPLAY.md`. Valorile numerice sunt cele găsite în cod, iar denumirile descriptive sunt folosite numai când comportamentul lor este susținut de citirea funcțiilor. Câmpurile pentru care semantica nu este sigură rămân marcate ca neconfirmate.

---

## 1. Sistemul de arme și proiectile

### 1.1. Arhitectura

Există două structuri distincte:

- `typeweaponshot` / `weaponshotkinds(1..40)`: definiții statice de tip/proiectil, configurate la inițializare în `Functions/EntryPoint.bb`.
- `weaponshots(1..100)`: instanțe active în timpul jocului, cu stare proprie, create prin `makeweaponshot()` și actualizate prin `doweaponshots()`.

`loadweaponshots()` este în prezent un stub gol. Asta înseamnă că parametrii proiectilelor nu sunt încărcați dintr-un fișier extern de acolo; definițiile vizibile sunt inițializate programatic în EntryPoint.

Fluxul unei împușcături:
1. Sistemul actorului decide că un mount poate trage și calculează poziția/heading-ul gurii țevii.
2. `makeweaponshot(x,y,angle,kind,owner,mount,extra)` verifică limitele ecranului (cu excepția tipurilor persistente în afara ecranului), caută un slot liber și copiază parametrii definiției în instanța activă.
3. Este creat un particle/sprite pentru reprezentarea vizuală; sunt inițializate proprietarul, mount-ul, viteza, damage-ul, filtrele și efectele.
4. `doweaponshots()` actualizează mișcarea, timpul de viață, rotația, culoarea, trail-ul, accelerația, gravitația, homing-ul și comportamentele speciale.
5. `doweaponcollision()` testează coliziunea, aplică efectele/damage-ul și poate genera particule sau explozii.

### 1.2. Ce se poate mapa sigur în `typeweaponshot`

| Câmp | Semnificație dedusă din utilizare | Încredere |
|---|---|---|
| Field0 | Durată/parametru folosit la interpolarea fade-ului de culoare | Medie |
| Field1–Field6 | Culori RGB inițiale/finale pentru sprite (copiate și interpolate) | Ridicată |
| Field7 | Flag/parametru auxiliar; tipurile 7–9 îl setează la 0 | Scăzută |
| Field12 | Limită/valoare de damage acumulat folosită la anumite coliziuni | Medie |
| Field14 | Index de layer/texture pentru animația proiectilului | Ridicată |
| Field15 | Identificatorul tipului de proiectil; în instanță este copiat ca ID | Ridicată |
| Field16 | Parametru numeric auxiliar neconfirmat | Scăzută |
| Field17 | Gravitație/accelerație verticală; este aplicat ca scădere din viteza Y | Ridicată |
| Field18 | Unghiul curent al proiectilului | Ridicată |
| Field19 | Viteza scalară/valoarea folosită pentru construirea vitezei X/Y | Ridicată |
| Field20 | Limită de viteză sau parametru de clamp al vitezei | Medie |
| Field21 | Damage/valoare de impact folosită în coliziune | Medie |
| Field24 | Temporizator de comportament; când este pozitiv, anumite ghidări sunt suspendate | Medie |
| Field25 | Permite proiectilului să existe/continue în afara limitelor ecranului | Ridicată |
| Field31–Field32 | Dimensiuni vizuale ale sprite-ului | Ridicată |
| Field33–Field34 | Dimensiuni/parametri auxiliari pentru coliziune ori efect | Scăzută |
| Field35 | Mod de aplicare a damage-ului: unele ramuri scad direct, altele scalează cu frame delta | Ridicată |
| Field38 | Comportament de întoarcere/mișcare specială, inclusiv starea de întoarcere | Medie |
| Field39–Field40 | Parametri de deplasare și timp pentru comportamentul special de întoarcere | Medie |
| Field41 | Index de sunet declanșat la creare | Ridicată |
| Field42 | Activează căutarea unei ținte | Ridicată |
| Field43 | Raza de căutare a țintei | Ridicată |
| Field44 | Viteza de virare a homing-ului | Ridicată |
| Field45 | Indexul țintei găsite | Ridicată |
| Field46 | Amplitudinea variației aleatorii a unghiului la creare | Ridicată |
| Field47 | Rata de modificare a vitezei scalară | Ridicată |
| Field48 | Factor de viteză/decay; instanța este incrementată cu Field47 × delta și eliminată dacă scade sub prag | Medie |
| Field49–Field50 | Accelerații pe X/Y | Ridicată |
| Field51 | Rata de modificare a unghiului/rotației | Medie |
| Field52–Field57 | Parametri de trail/efect periodic: interval, durată, dimensiune, culoare/layer și flag | Medie |
| Field60 | Factor de multiplicare/atenuare a vitezei în timp | Medie |
| Field61 | Culoare de muzzle flash (coduri 1–5) | Ridicată |
| Field62 | Activează un efect de particule la impact/expirare | Ridicată |
| Field63 | Dimensiune/putere a exploziei la impact | Ridicată |
| Field64[1..10] | Filtre/flag-uri per categorie de țintă sau interacțiune; sensul exact al fiecărui index nu este complet stabilit | Scăzută |
| Field65–Field68 | Parametri auxiliari pentru urmărire/atașare și efect vizual | Scăzută |
| Field69 | Timer/valoare de fade alpha/color | Medie |
| Field70 | Cooldown-ul de bază folosit de armele actorilor pentru acest tip de shot | Ridicată |
| Field71 | Slotul activ al instanței runtime | Ridicată |

**Atenție:** structura de definiție și structura runtime folosesc aceleași Field IDs, dar nu au exact același rol. De exemplu, Field71 în definiție nu este folosit ca slot runtime, în timp ce Field71 în `weaponshots` este flag-ul activ.

### 1.3. Tipurile de proiectile definite vizibil

EntryPoint configurează explicit cel puțin tipurile 1–9:

| ID | Caracteristici certe din configurare | Interpretare prudentă |
|---:|---|---|
| 1 | layer 22, damage 1.3, dimensiune 10×25, sunet 2, cooldown 8 | Proiectil simplu rapid |
| 2 | layer 21, damage 1.5, dimensiune 12×28, sunet 3, cooldown 7 | Variantă de proiectil simplu |
| 3 | layer 23, damage 1.7, dimensiune 14×32, sunet 1, cooldown 6 | Variantă mai puternică |
| 4 | layer 35, damage 17, homing activ, rază 100, cooldown 120, trail/explozie | Proiectil ghidat |
| 5 | layer 34, damage 2, dimensiune 12×21, sunet 10, cooldown 20 | Proiectil cu alt profil de impact |
| 6 | layer 33, damage 1.5, gravitație, accelerație și trail | Proiectil cu traiectorie curbată/efect special |
| 7 | layer 26, dimensiune foarte mare, accelerație negativă, persistent în afara ecranului | Fascicul/efect persistent |
| 8 | layer 25, parametri similari tipului 7 | Variantă de fascicul |
| 9 | layer 27, parametri similari tipului 7 | A treia variantă de fascicul |

Acestea sunt ID-uri de tip proiectil, nu nume comerciale confirmate ale armelor. Nu este sigur să deducem numele dintr-un singur layer sau din imaginea sprite sheet.

### 1.4. Configurarea armelor hamsterului

`doplayerweaponsetup()` compune nivelurile de upgrade din profil (`playersetup`) și upgrade-urile temporare ale jucătorului. Sunt cinci familii de upgrade:

- Cannon: nivel combinat, maximum 10 în anumite condiții; configurează până la cinci mount-uri și tipul de tun pentru fiecare.
- Missile: maximum 5; folosește tipul de shot 4 pe mount-ul 6.
- W-Laser: maximum 5; folosește mount-ul 7 și tipul de shot 6.
- Turret: maximum 5; activează un mount dedicat (8), cu tipul de turret 5.
- B-Laser: maximum 5; mount-ul 9, tipul de shot selectat prin `beamlasertype()`.

Tabelul `cannonnumber` definește numărul de țevi/mount-uri pentru nivelurile 1–10:
- Nivelurile 1–3: 1, 2, 3 țevi de tip 1.
- Nivelurile 4–6: 2, 3, 4 țevi de tip 2.
- Nivelurile 7–10: 3, 4, 5 țevi de tip 3.

Unghiurile țevilor sunt distribuite simetric, de la 0° pentru o țeavă la ±10° pentru cinci țevi. `beamlasertype` mapează nivelurile 1–2 la shot 7, 3–4 la shot 8 și 5 la shot 9.

Ratele de tragere pentru Missile, W-Laser, Turret și B-Laser sunt multiplicatori pe mount; nivelurile superioare reduc intervalul efectiv dintre focuri. Nu trebuie convertite direct în DPS până când toate formulele de timer și damage sunt normalizate.

### 1.5. Riscuri tehnice observate

- Pool-ul runtime are 100 sloturi. Dacă toate sunt ocupate, `makeweaponshot()` revine la slotul 1, fără să elibereze explicit instanța anterioară. Poate suprascrie un proiectil activ.
- Funcția de creare poate ieși dacă particle-ul vizual nu se creează; trebuie verificat dacă slotul parțial inițializat rămâne într-o stare consistentă.
- Damage-ul nu este uniform: Field35 selectează cel puțin două căi de decrementare, una directă și una scalată cu frame delta. Reimplementarea trebuie să păstreze semantica fiecărei familii.
- Proiectilele pot fi ghidate, pot avea gravitație, accelerare, revenire la proprietar, trail periodic și persistență în afara ecranului. Un singur script generic simplificat nu va reproduce comportamentul.
- `loadweaponshots()` gol indică faptul că definirea datelor și încărcarea asset-urilor sunt separate; în Godot, recomandăm Resource-uri de definiție explicite, nu încărcarea implicită a datelor din funcția runtime.

---

## 2. Inamici și boss-uri

### 2.1. Identificatori și arhitectură

`makeenemy(enemy_id,...)` creează un actor cu archetype ID `enemy_id + 2`. Prin urmare:
- Enemy ID 1 corespunde actor archetype 3.
- Enemy ID 2 corespunde actor archetype 4.
- etc.

Sunt alocate 30 de definiții `enemytypetype`, iar runtime-ul are 20 de sloturi `enemytype`, legate de sloturile actorilor. În datele de nivel se întâlnesc ID-uri de inamici până la 18. Asta nu dovedește că toate ID-urile 1–30 sunt folosite în campania standard.

AI-ul inamicilor nu este în `doenemies()` (stub gol). `doenemiesend()`, apelată din `doactorendai()`, conține logica specifică archetype-ului. `enemytype.Field5[1..100]` funcționează ca memorie de stare pentru aceste mașini de stări.

### 2.2. Familii vizuale identificate

Maparea din `artdefinitions()` confirmă următoarele resurse animate și poate ajuta la inventarierea vizuală. Nu echivalează automat cu o mapare completă enemy ID → nume:

| Resursă / familie grafică | Fișier |
|---|---|
| Basic flyer | `gfx/basicflyer.png` |
| Advanced flyer | `gfx/advancedflyer.png` |
| Hang glider | `gfx/hangglider4.png` |
| Zipjet | `gfx/zipjet.png` |
| Kamikaze | `gfx/kamikaze.png` |
| Saucer | `gfx/sauceranim.png` |
| Laser saucer | `gfx/lasersauceranim.png` |
| Large green saucer | `gfx/largesaucergreenanim.png` |
| Alien tub | `gfx/alientub.png` |
| Hovercraft | `gfx/hovercraft.png` |
| Heavy hovercraft | `gfx/heavyhovercraft.png` |
| Pogo | `gfx/pogo.png` |
| Energy sphere | `gfx/energysphere.png` |
| Big bird | `gfx/bigbird.png` |
| Stamper ship / foot | `gfx/stamper_ship.png`, `gfx/stamper_foot.png` |

Sunt și sprite-uri de proiectil/obiecte (`clusterbomb.png`, `energyshield.png`) care nu trebuie catalogate automat drept inamici.

### 2.3. Familii AI confirmate în cod

`doenemiesend()` selectează comportamente după `actors(slot).Field43`, adică archetype ID-ul actorului. În cod sunt prezente ramuri pentru:
- actor type 3, 10 și 11: mișcare verticală oscilantă, cu limite dependente de tip;
- actor type 13: comportament cu actor copil și alternare de vizibilitate/faction;
- actor type 17: actor copil care coboară, lovește limita inferioară, produce shake/dust și revine;
- alte ramuri de archetype cu deplasare, rotație, atac și/sau copil, care trebuie catalogate pe tot corpul funcției înainte de a le atribui nume comerciale.

Un inamic poate avea actor copil, iar acel copil poate fi folosit pentru hitbox, armă, efect sau parte vizuală. De aceea, catalogul de entități trebuie să includă relațiile parent-child, nu doar sprite-ul principal.

### 2.4. Spawn și dificultate

`makeenemy()`:
- copiază archetype-ul prin `makeactor()`;
- randomizează ținta X/Y și heading-ul inițial;
- inițializează scratch state-ul `Field5[1..100]`;
- dublează health-ul actorului în multiplayer;
- aplică multiplicatorul de dificultate asupra Field76[1..10] al armelor actorului: 1.75 / 1.45 / 1.15 / 0.85 pentru cele patru niveluri de dificultate; 2.0 în special mode 2 și 1.3 în celălalt special mode;
- incrementează contorul global și contorul per tip.

Nu etichetați automat acești multiplicatori drept „fire rate” fără testarea formulei de cooldown: sunt aplicați pe Field76 al fiecărui mount, care influențează timer-ul armelor, dar efectul exact trebuie verificat în contextul complet al funcției de tragere.

### 2.5. Boss-uri

Există 3 sloturi runtime pentru boss-uri, fiecare cu până la 10 componente actor. `makeboss()` citește `bosstypes(boss_id)`, creează componentele configurate și însumează health-ul lor. `dobosses()`:
- însumează health-ul părților active;
- actualizează bara de boss;
- mută originea boss-ului către o țintă aleatorie;
- repoziționează componentele după offset-urile lor;
- apelează `dobossai()`;
- declară boss-ul învins numai după ce nu mai există componente active.

`dobossai()` conține cinci familii de comportament (Case 1–5). Confirmat din cod:
- Case 2: două componente care coboară/urcă cu accelerare și impact la limita inferioară.
- Case 3: oscilație orizontală a unei componente și sincronizarea unghiurilor a două mount-uri.
- Case 4: după timer, generează inamici de tip 11 în jurul boss-ului.
- Case 5: gestionează secvențial mai multe componente și restaurează health-ul unor părți în funcție de ordinea distrugerii.
- Case 1 nu conține logică în funcția inspectată.

Asset-urile boss-urilor sunt:
`gfx/bosses/boss11.png`, `boss12.png`, `boss21.png`, `boss22.png`, `boss23.png`, `boss24.png`, `boss31.png`, `boss32.png`, `boss4.png`, `boss51.png`, `boss52.png`, `boss53.png`.

**Limită:** nu am atribuit nume canonice celor cinci boss-uri doar pe baza fișierelor grafice. Pentru un catalog complet boss ID → componente → faze → atacuri este necesară extragerea integrală a definițiilor `bosstypes` și corelarea cu `setbossevent()`, `dobossai()` și sprite-urile.

---

## 3. Bonusuri, power-up-uri și upgrade-uri

### 3.1. Pool și ciclu de viață

- Sunt 10 sloturi de bonus (`bonus(1..10)`).
- `makebonus(x,y,forced_type)` caută un slot liber, decide tipul aleatoriu în funcție de probabilități și de upgrade-urile disponibile, apoi setează sprite-ul și starea.
- `makecashbonus(x,y,value)` creează un bonus de bani cu ID intern `10 + value`.
- `dobonus()` actualizează căderea/mișcarea, detectează coliziunea cu unul dintre cei doi jucători și aplică efectul.
- După colectare, bonusul este micșorat și eliminat.

### 3.2. Catalogul efectelor

| ID bonus | Efect |
|---:|---|
| 1 | Cannon Upgrade |
| 2 | Missile Upgrade |
| 3 | B-Laser Upgrade |
| 4 | Turret Upgrade |
| 5 | W-Laser Upgrade |
| 6 | Extra Shield |
| 7 | Extra Life |
| 8 | Extra Bomb |
| 9 | Mega Shield! – setează timer-ul de shield la 900 și flag-ul de protecție |
| 10 | Mega Upgrade!!! – setează timer-ul de upgrade la 900 |
| 11 | $100 |
| 12 | $250 |
| 13 | $500 |
| 14 | $1000 |

Dacă un upgrade este deja la plafon sau nu poate fi aplicat, codul acordă în schimb $3000. Există și limitări de disponibilitate: upgrade-urile de armament sunt filtrate prin configurația `playersetup`, iar anumite power-up-uri sunt dezactivate în special mode 2.

### 3.3. Probabilități de generare

Codul alege un număr aleatoriu între 1 și 100:
- 1–40: unul dintre upgrade-urile 2–5;
- 41–60: Cannon Upgrade (ID 1);
- 61–90: Extra Shield sau Extra Bomb;
- intervalul final: Extra Life sau Mega Shield/Mega Upgrade, cu ramuri speciale.

Există o suprapunere la pragul 90 în condițiile scrise (`3D..5A`, apoi `>=5A`), deci valoarea exactă 90 poate intra în ambele ramuri succesiv, iar a doua poate suprascrie prima. Trebuie păstrat comportamentul efectiv, nu „corectată” distribuția presupusă, până la verificarea intenției autorului.

Mai există corecții de selecție:
- dacă jucătorul nu are cannon upgrade temporar și profilul permite, poate forța Cannon Upgrade;
- dacă limita de upgrade de cannon din eveniment nu este atinsă, poate forța cannon;
- dacă jucătorul are zero vieți suplimentare în anumite condiții, există o șansă să aleagă Extra Life;
- argumentul `forced_type` suprascrie selecția aleatorie.

### 3.4. Structura bonusului

`bonustype`:
- Field0: activ;
- Field1: handle-ul sprite/entity;
- Field2: ID-ul bonusului;
- Field3/Field4: poziția X/Y;
- Field5: viteză verticală;
- Field6: stare/parametru de animație (negativ după colectare);
- Field7: dimensiune;
- Field8: flag de cădere/limită.

### 3.5. Upgrade persistent vs upgrade temporar

Upgrade-urile colectate în timpul nivelului sunt stocate în structura jucătorului (`player.Field28..32`, plus shield/lives/bombs etc.) și se resetează/inițializează în funcție de pornirea nivelului. Nivelurile permanente vin din `profiles.dat` prin `playersetup`.

`doplayerweaponsetup()` combină nivelul permanent și cel temporar. În special mode, unele niveluri temporare sunt tratate diferit și pot ajunge la plafonul 10 pentru cannon, 5 pentru celelalte familii.

---

## 4. Profiluri, salvări și high-score

### 4.1. Profiluri

Sunt 10 sloturi de profil și un index de profil curent. Profilul 0 este tratat ca „fără profil” pentru setările de sunet/muzică; sloturile 1–10 sunt salvate în `profiles.dat`.

`profile` conține:
- Field0: numele profilului;
- Field1–Field16: valori numerice serializate în ordinea explicită din `saveprofiles()`.

Ordinea de serializare este:
`name, Field4, Field2, Field3, Field1, Field5, Field6, Field7, Field8, Field9, Field10, Field11, Field12, Field13, Field14, Field15, Field16`.

`loadprofiles()` citește exact aceeași ordine. Dacă fișierul lipsește, creează unul; dacă indexul curent este în afara 0..10, îl resetează la 0. Dacă Field15 este 0, îl normalizează la 1. Nu există în codul inspectat o versiune de schemă, checksum sau migrare a formatului.

`newprofile()` inițializează progresul și setările în funcție de `tempskill`, activează muzica și sunetul și scrie imediat fișierul. `changeprofile()` schimbă indexul curent și reîncarcă setările de sunet/muzică. `deleteprofile()` golește valorile profilului selectat și salvează fișierul.

### 4.2. Câmpurile profilului mapate funcțional

| Câmp | Utilizare confirmată |
|---|---|
| Field0 | Nume profil; folosit și la înregistrarea high-score-ului |
| Field2 | Setare sound on/off |
| Field4 | Setare music on/off |
| Field5 | Dificultate, citită de `makeenemy()` |
| Field7 | Valoare persistentă pentru Cannon, copiată în `playersetup.Field4` |
| Field8 | Valoare persistentă pentru B-Laser, copiată în `playersetup.Field6` |
| Field9 | Valoare persistentă pentru Missile, copiată în `playersetup.Field5` |
| Field10 | Valoare persistentă pentru Turret, copiată în `playersetup.Field8` |
| Field11 | Valoare persistentă pentru W-Laser, copiată în `playersetup.Field7` |
| Field12 | Valoare persistentă de configurare a jucătorului, copiată în `playersetup.Field0` |
| Field13 | Valoare persistentă de configurare a jucătorului, copiată în `playersetup.Field3` |
| Field15 | Valoare de setup copiată în `playersetup.Field9`; valoarea 0 este normalizată la 1 la load |
| Field16 | Valoare persistentă copiată în `playersetup.Field1` |

Field1, Field3, Field6 și Field14 sunt serializate și inițializate, dar semantica lor completă nu a fost confirmată în acest pas.

### 4.3. High-score

- Sunt 10 poziții.
- `highscore` are nume (Field0) și scor (Field1).
- `defaultscores()` creează tabela implicită cu nume presetate și scoruri fixe.
- `addscore(score)` caută poziția în clasament, mută în jos intrările inferioare și inserează numele profilului curent.
- `savescores()` scrie cele 10 perechi nume/scor în `scores.dat`; `loadscores()` citește aceeași ordine.
- `posthighscore()` pornește un executabil separat (`MightyScore.exe` sau `MightyScoreAff.exe`) cu un argument tokenizat. Este o integrare externă, nu un endpoint intern.

### 4.4. Riscuri și implicații pentru rebuild

- Formatul binar este implicit și fără versiune. Rebuild-ul nu trebuie să reutilizeze direct structura binară Blitz3D; recomandăm JSON versionat sau Resource-uri Godot plus salvări JSON.
- Salvarea actuală scrie direct în fișierul țintă; nu este vizibilă o strategie atomică temp-file + rename.
- Lipsesc verificări clare pentru fișiere trunchiate/corupte, nume invalide sau câmpuri absente.
- Ștergerea profilului setează `profiles(currentprofile).Field14 = 0`, nu `profiles(profiletodelete).Field14`. Poate fi intenționat sau bug; trebuie investigat înainte de replicare.
- High-score-ul este global pentru instalare, nu per profil, iar numele salvat este numele profilului activ.
- Integrarea MightyScore externă trebuie considerată componentă legacy și izolată în noul proiect.

---

## 5. Catalogul nivelurilor și progresia campaniei

### 5.1. Structură și generare

`levels` este alocat ca matrice `levelevent(0..40, 0..60)`: până la 40 de indexuri de nivel și 60 de evenimente per nivel. `EntryPoint.bb` conține datele de bază, iar `makemorelevels()`:
1. copiază un set sursă în `levels2`;
2. golește/normalizează câmpuri din matricea activă;
3. mapează nivelurile din matrice printr-un tabel de indexuri;
4. generează niveluri intermediare prin copierea evenimentelor din nivelul vecin;
5. randomizează alegerea sursei pentru unele evenimente;
6. ajustează timpii și transferă evenimentele de boss.

Prin urmare, matricea alocată nu înseamnă 40 de seturi de design complet independente. O parte din progresie este derivată/copiată procedural. Trebuie reconstruită logica de generare, nu doar copiate câteva linii de date.

### 5.2. Câmpurile evenimentului de nivel

| Câmp | Semnificație |
|---|---|
| Field0 | Momentul/timpul de declanșare al evenimentului |
| Field1 | Prerechizit/condiție legată de boss-uri învinse |
| Field2 | Identificator background |
| Field3 | Limită globală de inamici simultani |
| Field4 | Identificator muzică |
| Field5[1..5] | Tipuri de inamici pentru spawn scriptat |
| Field6[1..5] | Coordonate X pentru spawn scriptat |
| Field7[1..5] | Coordonate Y pentru spawn scriptat |
| Field8[1..5] | Heading pentru spawn scriptat |
| Field9[1..5] | Multiplicator health pentru inamicul scriptat |
| Field10[1..5] | Multiplicator per mount/armă pentru inamicul scriptat |
| Field11[1..5] | Flag suplimentar transmis la crearea inamicului |
| Field12[1] | ID boss |
| Field13[1], Field14[1] | Coordonatele X/Y ale boss-ului |
| Field15..Field20[1..6], Field21 | Date de obiecte/elemente de fundal scriptate; sensul complet rămâne parțial neconfirmat |
| Field22[1..5] | Tipurile de inamici pentru spawn dinamic |
| Field23[1..5] | Interval/parametru de randomizare pentru spawn dinamic |
| Field24[1..5] | Limită simultană per tip de inamic |
| Field25 | Text de anunț/mesaj de eveniment |
| Field26 | Marker de completare a nivelului |
| Field27 | Parametru de limitare a progresiei upgrade-ului de cannon |
| Field28 | ID de tranziție meniu |
| Field29–Field30 | Câmpuri text suplimentare; Field30 este copiat din Field29 în `copylevelevent()`, posibilă eroare de copiere |

### 5.3. Fluxul unui nivel

- `startlevel()` resetează contoarele și stările de nivel, stabilește configurația jucătorilor din profil și creează unul sau doi jucători.
- `currentevent` începe la 1, `leveltime` la 0, iar background-ul inițial este citit din primul eveniment.
- `dolevels()` avansează timpul și verifică evenimentul curent, prerechizitele de boss, muzica, mesajele, spawn-urile scriptate și condiția de completare.
- `controlenemies()` gestionează spawn-ul dinamic pe baza Field22–Field24, a limitei globale Field3 și a stării curente a inamicilor.
- `willwin` este activat de markerul Field26, dar terminarea nivelului așteaptă și ca numărul inamicilor rămași să scadă sub 1.
- Boss-urile sunt declanșate prin Field12–Field14; în unele niveluri, `setbossevent()` adaugă și valuri scriptate.

### 5.4. Ce se poate afirma despre campanie

- Matricea suportă până la 40 de indexuri de nivel (0..40) și 60 de evenimente (0..60).
- Datele de bază observate în EntryPoint folosesc niveluri de referință și evenimente cu timpi progresivi; `makemorelevels()` completează/derivă secvența.
- În setul de date de bază sunt configurate boss IDs 1–5 în evenimentele de referință, iar funcția `dobossai()` are cinci ramuri de comportament.
- Există evenimente speciale de boss la indexuri de nivel care declanșează `setbossevent()`, cu valuri de inamici și componente suplimentare.
- Câmpul Field4 selectează muzica, iar resursele audio din repository includ `menu.ogg`, `main.ogg` și `boss.ogg`.

**Important:** nu publicăm încă un tabel „Nivelul 1 = nume de lume X, Nivelul 2 = lume Y” deoarece identificatorii de background și maparea generată trebuie extrase complet, iar numele/ordinea lumilor nu sunt toate codificate textual. Un tabel numeric complet poate fi generat ulterior prin parsarea tuturor asignărilor și simularea `makemorelevels()`.

---

## 6. Recomandări de modelare pentru Godot 4

Nu traduceți structurile Blitz3D Field-for-Field. Folosiți date tipizate și definiții declarative:

- `WeaponDefinition` (Resource): id, texture/layer, muzzle_flash, fire_interval, speed, damage, lifetime, collision_profile, homing, gravity, acceleration, trail, impact_effect.
- `ProjectileInstance` (Node2D): owner_id, mount_id, velocity, target_id, age, runtime_flags.
- `EnemyDefinition` (Resource): archetype, sprite/animation, health, collision_shape, movement_profile, weapon_mounts, child_parts, faction.
- `EnemyController` / state machine: stări numite explicit, fără `Field5[100]`.
- `BossDefinition` (Resource): parts, offsets, health, AI profile, spawn phases.
- `PickupDefinition` (Resource): pickup_type, effect, eligibility, weight, value.
- `PlayerUpgradeState`: nivel permanent, nivel temporar, limite și efective calculate.
- `LevelDefinition` (Resource): evenimente ordonate explicit, cu timestamp, spawn-uri, boss trigger, background, music și completion rule.
- `ProfileSave`: JSON versionat cu `schema_version`, validare, backup și scriere atomică.
- `HighScoreEntry`: listă ordonată, cu nume și scor.

### Ordinea recomandată de implementare

1. Parser/extractor offline pentru datele statice din EntryPoint și generarea unui export JSON de referință.
2. Definițiile armelor și testele unitare pentru viteză, damage, lifetime și coliziune.
3. Sistemul de pickup/upgrade, cu plafon și reguli de eligibilitate.
4. ProfileSave și HighScoreStore, independente de gameplay.
5. Catalogul EnemyDefinition și implementarea stărilor AI pe familii.
6. BossDefinition și sistemul de părți multiple.
7. LevelDefinition + generatorul de progresie; verificarea numărului de evenimente și a ordinii lor.
8. Abia apoi integrarea tuturor sistemelor în bucla principală.

---

## 7. Concluzii și limite ale acestui pas

Acest audit a extins analiza către arme/proiectile, upgrade-uri, bonusuri, profiluri, high-score și generarea nivelurilor. Sunt confirmate mecanismele majore și o parte importantă din schema datelor.

Nu sunt încă închise:
- maparea completă a tuturor câmpurilor din `typeweaponshot`;
- extragerea completă a tuturor archetype-urilor și a statisticilor fiecărui inamic;
- maparea exactă boss ID → sprite-uri → componente → faze;
- exportul integral al celor 40 × 60 evenimente după rularea algoritmului `makemorelevels()`;
- semantica tuturor câmpurilor profilului și a câmpurilor Field15–Field21 din evenimentul de nivel.

Acestea sunt limite ale auditului static curent, nu afirmații despre lipsa datelor în executabil. Nu s-a efectuat comparația dinamică cu Mighty Rodent original, conform scopului stabilit pentru acest pas.
