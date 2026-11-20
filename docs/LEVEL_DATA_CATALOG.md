# Audit: datele nivelurilor, evenimente și generarea progresiei

**Repository:** `Iulian-hub/RUNAWAY-RABBIT-DECOMP`, branch `main`  
**Surse inspectate:** `Functions/EntryPoint.bb`, `Functions/makemorelevels.bb`, `Functions/copylevelevent.bb`, `Functions/dolevels.bb`, `Functions/startlevel.bb`, `Functions/setbossevent.bb`, `Functions/speciallevelmode.bb`, `Functions/dospeciallevelmode.bb`, `Types/levelevent.bb`.  
**Metodă:** analiză statică a codului decompilat. Valorile temporale sunt păstrate în unitățile interne ale jocului; nu sunt convertite în secunde fără confirmarea exactă a unității lui `fl\Field1`.

---

## 1. Rezumatul descoperirilor

Sistemul de niveluri are trei straturi distincte:

1. **Date-sursă / template-uri** inițializate în `EntryPoint.bb`.
2. **Generare procedurală** prin `makemorelevels()`, care remapează template-urile și umple nivelurile intermediare prin copiere alternantă din niveluri vecine.
3. **Execuție runtime** prin `startlevel()` și `dolevels()`, unde evenimentele se declanșează în funcție de timp și, opțional, de numărul de boss-uri învinse.

Nu este corect să tratăm toate cele 40 de sloturi din array ca 40 de niveluri de campanie definite manual. În sursa inspectată:
- sunt inițializate explicit 15 template-uri principale (ID 1–15);
- există date auxiliare în sloturile 37–40;
- sloturile 16–36 nu au aceeași structură de template complet configurat;
- `makemorelevels()` construiește progresia efectivă pentru intervalul 1–29 și copiază separat sloturile 36–40.

**Important:** selecția vecinului din generarea nivelurilor folosește `rand(1,2)`. Prin urmare, cel puțin o parte din nivelurile intermediare pot varia între inițializări. Nu trebuie presupus că fiecare rulare produce aceeași secvență exactă.

---

## 2. Structura `levelevent`

Tipul are 31 de câmpuri, dintre care mai multe sunt array-uri:

| Câmp | Utilizare observată |
|---|---|
| Field0 | Pragul temporal la care evenimentul poate deveni eligibil |
| Field1 | Numărul minim de boss-uri învinse necesar; 0 înseamnă fără condiție |
| Field2 | ID background; folosit și ca indicator de inițializare în `makemorelevels()` |
| Field3 | Limită agregată de inamici pentru eveniment; inițial este configurată și ulterior recalculată ca suma Field24[1..5] |
| Field4 | ID muzică; valoarea 3 este folosită de generator ca reper special pentru secvențele de boss |
| Field5[1..5] | Enemy ID-uri pentru spawn-uri explicite |
| Field6[1..5] | X pentru spawn-urile explicite |
| Field7[1..5] | Y pentru spawn-urile explicite |
| Field8[1..5] | Heading pentru spawn-urile explicite |
| Field9[1..5] | Multiplicator HP aplicat actorului nou creat, dacă este pozitiv |
| Field10[1..5] | Multiplicator pentru rata de tragere a mount-urilor |
| Field11[1..5] | Flag transmis mai departe către `makeenemy()` |
| Field12[1] | Boss type ID; valoare pozitivă declanșează `makeboss()` |
| Field13[1] | Parametru numeric transmis ca poziție X către `makeboss()` |
| Field14[1] | Parametru numeric transmis ca poziție Y către `makeboss()` |
| Field15[1..6] | ID tip de entitate de fundal; condiționează apelul `makebackgroundenemy()` |
| Field16[1..6] | Parametru numeric transmis către `makebackgroundenemy()` |
| Field17[1..6] | Parametru numeric transmis către `makebackgroundenemy()` |
| Field18[1..6] | Coordonată X transmisă către `makebackgroundenemy()` |
| Field19[1..6] | Coordonată Y transmisă către `makebackgroundenemy()` |
| Field20[1..6] | Parametru auxiliar copiat împreună cu definiția entității de fundal; semantica exactă nu este încă demonstrată |
| Field21 | Parametru scalar auxiliar copiat împreună cu datele de fundal; semantica exactă nu este încă demonstrată |
| Field22[1..5] | Enemy ID-uri pentru spawn dinamic |
| Field23[1..5] | Interval / parametru de temporizare pentru spawn-ul dinamic |
| Field24[1..5] | Limită concurentă per tip pentru spawn-ul dinamic |
| Field25 | Text de anunț afișat ca score-fly-up |
| Field26 | Flag de finalizare; setează `willwin` când evenimentul este executat |
| Field27 | Copiat de `copylevelevent()`; semnificația runtime nu este stabilită în acest audit |
| Field28 | ID de meniu transmis către `changemenu()` |
| Field29 | String auxiliar; utilizarea nu a fost identificată în traseul de execuție al nivelului |
| Field30 | String auxiliar; există o anomalie de copiere documentată mai jos |

### Clarificare despre Field3

La inițializare, pentru evenimentele din primele 20 de poziții, EntryPoint însumează `Field24[1..5]` și scrie rezultatul în `Field3`. Așadar, Field3 nu trebuie interpretat ca un număr independent de inamici ales arbitrar: în configurația respectivă este o limită agregată derivată din limitele per tip. Nu am confirmat în acest pas toate locurile în care runtime-ul consumă Field3.

### Clarificare despre Field15–Field21

Apelul efectiv este:

```blitzbasic
makebackgroundenemy(Field18[i], Field19[i], Field15[i], Float(Field16[i]), Float(Field17[i]))
```

Field20 și Field21 sunt copiate de generator, dar nu sunt argumente ale acestui apel. Nu le atribuim o semnificație mai precisă până la auditarea implementării `makebackgroundenemy()` și a tuturor referințelor lor.

---

## 3. Template-urile principale

Tabelul următor descrie datele brute inițializate în `EntryPoint.bb`, înainte de remaparea din `makemorelevels()`. „Enemy types” sunt ID-urile distincte găsite în `Field22[1..5]`; „scripted” sunt ID-urile distincte găsite în `Field5[1..5]`. Acestea nu reprezintă numărul total de spawn-uri, ci catalogul de tipuri prezente în template.

| Template ID | Evenimente inițializate | Ultimul prag temporal observat | Enemy types dinamice | Spawn-uri explicite | Boss type |
|---:|---:|---:|---|---|---:|
| 1 | 11 | 10800 | 1, 2, 7, 12 | 13 | — |
| 2 | 12 | 13200 | 1, 2, 3, 7, 12 | 5 | — |
| 3 | 14 | 14700 | 2, 3, 6, 7, 12 | — | 1 |
| 4 | 16 | 18000 | 2, 3, 5, 6, 7, 12, 13 | 9 | — |
| 5 | 16 | 18000 | 2, 3, 5, 6, 7, 12, 13, 17 | 8 | — |
| 6 | 14 | 14700 | 3, 5, 6, 7, 10, 12, 13, 17 | — | 2 |
| 7 | 15 | 18000 | 3, 5, 6, 7, 8, 10, 12, 13, 17 | 14 | — |
| 8 | 16 | 16800 | 3, 5, 6, 8, 9, 10, 12, 13 | 16 | — |
| 9 | 14 | 14700 | 3, 5, 8, 9, 10, 12, 13, 14, 17 | — | 3 |
| 10 | 16 | 18300 | 3, 4, 5, 8, 9, 10, 12, 13, 14, 17 | 11, 14 | — |
| 11 | 16 | 18000 | 4, 5, 8, 9, 10, 13, 14, 16, 17 | 15 | — |
| 12 | 14 | 14700 | 4, 8, 9, 12, 14, 16, 17 | — | 4 |
| 13 | 16 | 18300 | 4, 8, 10, 11, 13, 14, 16, 17 | 10 | — |
| 14 | 16 | 18000 | 4, 5, 8, 9, 10, 14, 15, 16, 17 | 15 | — |
| 15 | 14 | 14700 | 4, 8, 14, 15, 16, 17, 18 | — | 5 |

Valorile temporale sunt praguri interne din Field0. În cele cinci template-uri de boss (3, 6, 9, 12, 15), evenimentul boss apare la pragul 14700 în datele brute. După remapare, acestea devin nivelurile-seed 3, 7, 13, 21 și 29. În fiecare template există un eveniment de schimbare muzicală la track 3 înainte de evenimentul boss.

### Observație despre evenimente

Un template poate conține:
- spawn-uri explicite (Field5–11);
- spawn-uri dinamice controlate prin Field22–24;
- schimbări de muzică;
- apariția unui boss;
- entități de fundal;
- mesaje și condiții de finalizare.

Un singur eveniment poate activa mai multe dintre aceste subsisteme.

---

## 4. Remaparea template-urilor și generarea nivelurilor 1–29

### 4.1. Copierea inițială

`makemorelevels()` face mai întâi o copie completă a array-ului `levels` în `levels2`, pentru ID-urile 1–40 și evenimentele 1–60. Apoi șterge Field2 din toate evenimentele din `levels`, pentru a distinge nivelurile încă nepopulate.

### 4.2. Tabelul de remapare

Pentru fiecare template-sursă 1–15, generatorul îl copiază într-un slot de nivel rezultat. Direcția copierii este importantă: `levels(local2, local1)` primește datele din `levels2(local0, local1)`. Tabelul corect este:

| Template sursă | Nivel-seed rezultat |
|---:|---:|
| 1 | 1 |
| 2 | 2 |
| 3 | 3 |
| 4 | 4 |
| 5 | 5 |
| 6 | 7 |
| 7 | 9 |
| 8 | 11 |
| 9 | 13 |
| 10 | 16 |
| 11 | 19 |
| 12 | 21 |
| 13 | 24 |
| 14 | 27 |
| 15 | 29 |

Pentru sloturile 36–40, generatorul copiază fiecare slot din el însuși. În intervalul campaniei 1–29, nivelurile-seed sunt 1, 2, 3, 4, 5, 7, 9, 11, 13, 16, 19, 21, 24, 27 și 29. Celelalte niveluri din interval sunt completate procedural în etapa următoare.

### 4.3. Umplerea nivelurilor 1–29

A doua parte a funcției parcurge nivelurile 1–29. Nivelurile-seed sunt cele enumerate în tabel; sloturile dintre ele sunt completate procedural. Pentru fiecare nivel care încă nu are Field2 setat în primul eveniment:

1. Caută spre dreapta și spre stânga cel mai apropiat nivel-sursă eligibil.
2. Alege aleator unul dintre cei doi vecini, folosind `rand(1,2)`.
3. Dacă un vecin nu mai este eligibil, forțează alegerea celuilalt.
4. Copiază evenimentele de la vecinul ales, apoi rescrie Field0 cu un program temporal derivat din indexul evenimentului.
5. Dacă evenimentul anterior avea Field4 = 3, aplică o temporizare specială și activează traseul de configurare a evenimentului de boss.
6. Copiază setările Field15–Field21 de la evenimentul corespunzător al vecinului în evenimentul anterior al nivelului generat.
7. La detectarea reperului muzical Field4=3, marchează evenimentul următor cu Field26=1, apelează `zeroenemiesandbkgs()` și `setbossevent()`, apoi oprește propagarea obișnuită prin flag-ul local de finalizare.

Generatorul folosește și condiții de vecinătate bazate pe `Field2` din primul eveniment și pe distanța dintre ID-uri. Aceste condiții împiedică selectarea necontrolată a unui nivel-sursă prea îndepărtat sau deja epuizat.

### 4.4. Implicații

- Nivelurile intermediare dintre seed-uri sunt construite procedural din datele vecinilor; nu sunt seturi independente de date statice.
- Secvențele de boss sunt tratate separat prin detectarea muzicii track 3.
- Alegerea aleatorie a vecinului înseamnă că ordinea de conținut poate varia.
- Nu trebuie reconstruită campania doar prin exportarea datelor brute din EntryPoint: este necesară și reproducerea algoritmului `makemorelevels()`, sau înlocuirea lui intenționată cu o progresie deterministă documentată.

### 4.5. Configurația suplimentară de boss

`setbossevent()` nu este un simplu alias pentru `makeboss()`. În funcție de ID-ul nivelului rezultat, configurează până la cinci spawn-uri explicite în Field5–Field11. Sunt definite cazuri pentru nivelurile 6, 8, 10, 12, 14, 15, 17, 18, 20, 22, 23, 25, 26 și 28. Tipurile folosite includ Enemy ID 4, 10, 11, 14, 15 și 16, cu poziții, heading-uri, multiplicatori HP și multiplicatori de tragere specifici.

Boss-ul propriu-zis este creat separat de `dolevels()` atunci când Field12[1] este pozitiv.

---

## 5. Execuția runtime a evenimentelor

### 5.1. Inițializarea nivelului

`startlevel(level_id, player_count, special_start)`:
- calculează `longesttime` ca maximul Field0 pentru evenimentele 1–60;
- resetează contorii de inamici, boss-uri învinse, combo, bonus timer și stările modurilor speciale;
- setează echiparea jucătorilor din profil sau folosește configurația specială dacă este cerută;
- setează `currentevent=1`, `currentlevel=level_id`, `leveltime=0`;
- creează unul sau doi jucători;
- pornește muzica și setează fundalul din Field2 al primului eveniment.

Timpul de pregătire este 180 unități în modul normal și 60 în modul de start special.

### 5.2. Condiția de declanșare

`dolevels()` examinează evenimentul următor, adică `levels(currentlevel, currentevent + 1)`. Evenimentul este executat numai dacă:

- Field0 este pozitiv;
- Field0 este mai mic sau egal cu `leveltime`;
- `bossesdead >= Field1`, sau Field1 este 0.

Prin urmare, Field1 este o condiție de progresie, nu un al doilea timer.

### 5.3. Accelerarea timpului intern

Înainte de verificarea evenimentului, `leveltime` este incrementat cu `fl\Field1 * multiplier`, dacă nu a ajuns deja la pragul următor:

- nivelul 1: multiplier 2.2;
- nivelurile normale: multiplier 1.5;
- modurile speciale: multiplier 1.0.

Aceasta înseamnă că valorile Field0 nu se traduc direct în secunde reale fără să ținem cont de delta de frame și de multiplicatorul nivelului.

### 5.4. Acțiunile unui eveniment

Când condiția este îndeplinită, jocul:
1. incrementează `currentevent`;
2. recalculează contorii per tip de inamic;
3. schimbă muzica dacă Field4 este pozitiv;
4. setează `willwin` dacă Field26=1 și jocul nu este deja în game-over/win;
5. afișează Field25 dacă există text;
6. schimbă meniul dacă Field28 este pozitiv;
7. creează până la cinci inamici expliciți din Field5–Field11;
8. aplică multiplicatorii Field9 și Field10 actorilor creați;
9. creează boss-ul din Field12–Field14;
10. creează până la șase entități de fundal din Field15–Field19.

Spawn-urile dinamice Field22–Field24 sunt gestionate de sistemul de control al inamicilor, nu de bucla explicită de spawn Field5 din acest fragment.

### 5.5. Condiția de victorie

Field26 nu încheie instant nivelul. El setează `willwin`; victoria începe numai când `currentenemies < 1`. Apoi este afișat mesajul Level Complete sau You Win, iar tranziția către meniul rezultatului se produce după timerul de victorie.

În campania principală, mesajul special You Win este folosit când `currentlevel = 29`. Este folosit și în modurile speciale.

---

## 6. Sloturi auxiliare și moduri speciale

Datele din sloturile 37–40 sunt distincte de template-urile principale:

| Slot | Date observate | Interpretare prudentă |
|---:|---|---|
| 37 | 15 evenimente, background ID 3, spawn-uri dinamice | Set de evenimente separat; rolul exact în fluxul de meniu/moduri trebuie confirmat |
| 38 | Un eveniment, background ID 2 | Probabil scenă/slot auxiliar; nu are o secvență de gameplay completă în datele inspectate |
| 39 | 59 evenimente, background ID 4, cinci evenimente boss (tipurile 1–5), două ID-uri muzicale | Secvență de tip boss gauntlet / test extins; denumirea exactă din UI nu este încă confirmată |
| 40 | 7 evenimente, background ID 5, boss types 1–5 declanșate la pragul 10 | Secvență de test/arena cu toți boss-ii; rolul exact în UI nu este încă confirmat |

### 6.1. Modurile speciale runtime

`speciallevelmode()` definește două evenimente speciale:

- **Mode 1 – Coin Drop:** durată 900 unități; afișează COIN DROP!!!; `dospeciallevelmode()` creează bonusuri cash aleatorii.
- **Mode 2 – Look Out:** durată 600 unități; afișează LOOK OUT!!!; selectează Enemy ID 10 sau 17 la nivelurile timpurii și poate trece la ID 18 la nivelurile mai avansate; crește numărul de inamici permis odată cu nivelul.

În timpul modului 2, spawn-ul este limitat de `speciallevelmodenumber`. Poziția și latura de spawn sunt alese din regulile `enemytypes`; direcția de intrare este 90° sau -90° în funcție de marginea aleasă.

În modul normal, `dolevels()` poate declanșa un mod special după mai mult de 2200 unități fără un asemenea eveniment, cât timp progresul temporal este sub 80%. Dacă nivelul a trecut de jumătate și nu a avut coin drop, selectează Mode 1; altfel selectează Mode 2.

---

## 7. Anomalii și riscuri

1. **Copiere greșită posibilă în `copylevelevent()`:** ultima linie copiază `arg1\Field29` atât în Field29, cât și în Field30. Field30 din sursă nu este copiat. Pare o eroare de copiere/decompilare sau un bug original; nu o corectăm fără comparație cu executabilul.
2. **Câmpuri încă neinterpretate:** Field27, Field29 și Field30 nu au semnificație runtime stabilită în auditul curent.
3. **Câmpuri de background parțial mapate:** Field20 și Field21 sunt propagate de generator, dar nu sunt argumente directe pentru `makebackgroundenemy()`.
4. **Generator dependent de RNG:** reproducerea exactă a unei campanii necesită aceeași ordine și aceeași stare RNG, nu doar aceleași template-uri.
5. **Ordinea evenimentelor este secvențială:** runtime verifică numai evenimentul următor; un eveniment blocat de Field1 poate împiedica procesarea evenimentelor ulterioare chiar dacă timpul lor a trecut.
6. **Finalizarea este condiționată de curățarea inamicilor:** Field26 nu înseamnă „termină acum”; setează o stare care așteaptă eliminarea inamicilor activi.
7. **Sloturile 30–35 nu sunt populate de remaparea inspectată:** nu trebuie prezentate drept niveluri complete fără o altă cale de inițializare.
8. **Nu există în acest audit verificare dinamică:** pragurile și tranzițiile sunt deduse din sursă, nu măsurate în executabil.

---

## 8. Recomandare de reconstrucție Godot 4

Nu portați structura `levelevent` ca 31 de câmpuri anonime. Folosiți date denumite și resurse tipizate:

- `LevelDefinition`: ID, background, lista de evenimente.
- `LevelEvent`: trigger time, required bosses defeated, music, scripted spawns, dynamic spawn groups, boss spawn, background actors, announcement, completion action.
- `EnemySpawn`: enemy definition, position, heading, HP multiplier, fire-rate multiplier, flags.
- `DynamicSpawnGroup`: enemy definition, interval, concurrent cap.
- `BossSpawn`: boss definition, position.
- `BackgroundActorSpawn`: type, parameters, position.
- `LevelGenerator`: generator determinist cu seed explicit, dacă se dorește păstrarea variației procedurale.
- `LevelDirector`: execută numai următorul eveniment eligibil și verifică atât timpul, cât și condițiile de progresie.

### Recomandare fermă

Pentru prima versiune Godot, păstrați toate datele de nivel într-un format static și determinist, cu seed fix sau cu niveluri generate offline. După ce gameplay-ul de bază este validat, se poate reintroduce RNG-ul original. Astfel, bug-urile de gameplay nu vor fi confundate cu diferențele dintre două generări ale campaniei.

---

## 9. Ce rămâne pentru auditul următor

- Urmărirea completă a Field20/Field21 și a Field27/Field29/Field30 în toate funcțiile.
- Auditarea `makebackgroundenemy()` și a ciclului de viață al entităților de fundal.
- Identificarea modului în care sloturile 37–40 sunt selectate din meniuri și profile.
- Exportul complet al tuturor evenimentelor după `makemorelevels()`, inclusiv o simulare a RNG-ului și a stărilor de boss.
- Verificarea dacă anomalia Field30 este prezentă și în executabilul original.

Acest document descrie codul Gusto-modificat/decompilat. Nu afirmă că progresia rezultată este identică cu cea a executabilului original Mighty Rodent.
