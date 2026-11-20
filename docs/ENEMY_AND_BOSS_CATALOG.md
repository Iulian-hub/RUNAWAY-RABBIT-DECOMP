# Catalog aprofundat: inamici, archetype-uri și boss-uri

**Repository:** `Iulian-hub/RUNAWAY-RABBIT-DECOMP`, branch `main`  
**Surse principale:** `Functions/EntryPoint.bb`, `Functions/artdefinitions.bb`, `Functions/makeactor.bb`, `Functions/makeenemy.bb`, `Functions/doenemiesend.bb`, `Functions/makeboss.bb`, `Functions/dobossai.bb`, `Functions/dobosses.bb`, `Functions/setbossevent.bb`.  
**Metodă:** analiză statică a sursei decompilate. Nu reprezintă încă verificare în timpul rulării.

Acest document înlocuiește interpretările prea generale din auditul inițial cu o mapare concretă între Enemy ID, actor archetype, grafică și parametrii configurați în EntryPoint.

---

## 1. Modelul de entități

### 1.1. Enemy ID vs Actor archetype

`makeenemy(enemy_id,...)` apelează `makeactor(..., enemy_id + 2,...)`. Prin urmare, Enemy ID nu este egal cu indexul din `actortypes`.

| Enemy ID | Actor archetype | Grafică animată | Observație |
|---:|---:|---|---|
| 1 | 3 | `gfx/hangglider4.png` | Hang glider |
| 2 | 4 | `gfx/basicflyer.png` | Basic flyer |
| 3 | 5 | `gfx/advancedflyer.png` | Advanced flyer |
| 4 | 6 | `gfx/zipjet.png` | Zipjet |
| 5 | 7 | `gfx/divefighter.png` | Dive fighter |
| 6 | 8 | `gfx/kamikaze.png` | Kamikaze |
| 7 | 9 | `gfx/alientub.png` | Alien tub |
| 8 | 10 | `gfx/hovercraft.png` | Hovercraft |
| 9 | 11 | `gfx/heavyhovercraft.png` | Heavy hovercraft |
| 10 | 12 | `gfx/pogo.png` | Pogo |
| 11 | 13 | `gfx/energysphere.png` | Energy sphere; are actor copil archetype 21 |
| 12 | 14 | `gfx/sauceranim.png` | Saucer |
| 13 | 15 | `gfx/largesaucergreenanim.png` | Large green saucer |
| 14 | 16 | `gfx/lasersauceranim.png` | Laser saucer |
| 15 | 17 | `gfx/stamper_ship.png` | Stamper ship; are actor copil archetype 22 |
| 16 | 18 | `gfx/bigbird.png` | Big bird; are actor copil archetype 20 |
| 17 | 19 | `gfx/clusterbomb.png` | Entitate de tip cluster-bomb |
| 18 | 20 | `gfx/clusterbomb.png` | A doua variantă de cluster-bomb |

Această listă rezultă din asocierea `Field17` (index animație) cu `animloopgraphics` din `artdefinitions()`. Pentru archetype-urile cu grafică statică, `Field16` selectează `actorgraphics`, nu `animloops`.

### 1.2. Parametri de bază ai archetype-urilor

Valorile de mai jos sunt copiate de `makeactor()` din `actortypes` în actorul runtime. Health este valoarea de bază înainte de multiplicatorul multiplayer/dificultate. Dimensiunile sunt valorile de configurare din sursă, nu dimensiuni în pixeli garantate.

| Enemy ID | HP inițial | Speed de bază | Size X×Y | Collision extents X×Y | Mount-uri armă |
|---:|---:|---:|---:|---:|---:|
| 1 | 3 | 2.0 | 50×50 | 40×40 | 1 |
| 2 | 5 | 3.4 | 50×50 | 30×10 | 1 |
| 3 | 8 | 2.0 | 50×50 | 40×10 | 1 |
| 4 | 28 | 3.0 | 50×50 | 40×30 | 1 |
| 5 | 12 | 5.0 | 50×50 | 40×30 | 1 |
| 6 | 8 | 3.5 | 50×50 | 45×40 | 0 |
| 7 | 9 | 2.5 | 50×50 | 30×30 | 1 |
| 8 | 20 | 1.0 | 50×50 | 40×30 | 1 |
| 9 | 70 | 0.6 | 100×100 | 80×50 | 2 |
| 10 | 16 | 3.5 | 50×50 | 40×40 | 0 |
| 11 | 16 | 5.0 | 50×50 | 40×40 | 1 |
| 12 | 25 | 3.6 | 50×50 | 40×25 | 1 |
| 13 | 100 | 2.0 | 100×100 | 80×40 | 2 |
| 14 | 60 | 3.6 | 50×50 | 40×25 | 1 |
| 15 | 300 | 3.0 | 100×100 | 80×60 | 1 |
| 16 | 150 | 2.0 | 100×100 | 80×60 | 1 |
| 17 | 12 | 1.0 | 50×50 | 40×10 | 0 |
| 18 | 22 | 1.0 | 50×50 | 40×60 | 0 |

**Atenție la interpretare:** speed-ul este valoarea inițială din archetype; `makeenemy()` o randomizează între 0.75× și 1.5×. AI-ul poate apoi să o modifice în timpul jocului. HP-ul este multiplicat cu 2 în multiplayer. Valorile nu trebuie tratate ca statistici finale fixe.

### 1.3. Tipuri de armament ale inamicilor

ID-urile de mai jos sunt valorile `actortypes.Field40[mount]`, copiate în `actors.Field61[mount]` de `makeactor()`. Sunt ID-uri din `weaponshotkinds`, nu nume de arme.

| Enemy ID | Shot type pe mount-uri |
|---:|---|
| 1 | 11 |
| 2 | 11 |
| 3 | 12 |
| 4 | 15 |
| 5 | 15 |
| 6 | niciun mount configurat |
| 7 | 14 |
| 8 | 15 |
| 9 | 11, 11 |
| 10 | niciun mount configurat |
| 11 | niciun mount configurat în definiția de bază |
| 12 | 13 |
| 13 | 13, 13 |
| 14 | 10 |
| 15 | niciun mount configurat în definiția de bază |
| 16 | 13 |
| 17 | niciun mount configurat |
| 18 | niciun mount configurat |

Unele entități atacă prin comportament AI, copil sau prin mecanici speciale, nu printr-un mount standard. Nu deduceți „nu atacă” doar dintr-un mount absent.

### 1.4. Configurația EnemyType

`enemytypetype` are următoarele câmpuri folosite de sistemul de spawn:

- Field0: limită inferioară Y implicită, dacă este nenulă.
- Field1: limită superioară Y implicită, dacă este nenulă.
- Field2[1..3]: flag-uri de permisiune pentru cele trei direcții/laturi de spawn.
- Field3: scor/recompensă de bază asociată tipului, folosită de sistemul de scor.

Valorile Field3 definite în EntryPoint, convertite din hex în decimal:

| Enemy ID | Field3 |
|---:|---:|
| 1 | 50 |
| 2 | 100 |
| 3 | 300 |
| 4 | 500 |
| 5 | 400 |
| 6 | 300 |
| 7 | 250 |
| 8 | 450 |
| 9 | 700 |
| 10 | 400 |
| 11 | 600 |
| 12 | 200 |
| 13 | 500 |
| 14 | 200 |
| 15 | 1200 |
| 16 | 900 |
| 17 | 300 |
| 18 | 500 |

Câmpurile Field0–Field2 sunt suprascrise numai pentru unele tipuri. Când nu există override, logica din `doenemiesend()` folosește limitele generale aproximative Y=-50..275. Pentru Enemy ID 5, 15 și 16 există limite/flag-uri speciale în definiții.

---

## 2. Comportamente AI ale inamicilor

AI-ul este împărțit: `doactorai()` apelează funcțiile de jucător și `doenemies()`, dar `doenemies()` este stub gol în această versiune. Logica specifică a inamicilor se află în `doenemiesend()`, apelată din `doactorendai()`.

`enemytype.Field5[1..100]` este folosit ca memorie de stare. Nu este o structură de date tipizată: aceiași indici pot avea sens diferit pentru archetype-uri diferite.

### 2.1. Comportamente confirmate

| Actor archetype | Enemy ID | Comportament confirmat din cod |
|---:|---:|---|
| 3 | 1 | Oscilație verticală între limite; schimbă direcția când atinge pragurile verticale. |
| 4 | 2 | Nu are ramură individuală explicită în `doenemiesend()`; folosește mișcarea/tragerea generică. |
| 5 | 3 | Își modifică viteza de deplasare gradual și ajustează multiplicatorul de tragere pentru mount-ul 1. |
| 6 | 4 | Similar cu archetype 5 pentru accelerarea vitezei și ajustarea ratei mount-ului 1; setează unghiul turelei în funcție de orientare. |
| 7 | 5 | Mașină de stări pentru schimbarea heading-ului: țintește unghiuri laterale, coboară, apoi revine spre unghiul de patrulare; dezactivează tragerea într-o stare. |
| 8 | 6 | La detectarea direcției/țintei, virează agresiv spre țintă; dublează viteza în atac și folosește unghiuri de apropiere în jurul 170°/190°. |
| 9 | 7 | Nu are ramură individuală explicită în această funcție. |
| 10 | 8 | Mișcare verticală oscilantă; viteza crește gradual. |
| 11 | 9 | Mișcare verticală oscilantă; viteza crește gradual. Are două mount-uri. |
| 12 | 10 | Salturi verticale: accelerează/cade, produce efect de impact și schimbă direcția orizontală aleatoriu. |
| 13 | 11 | Controlează actorul copil archetype 21; alternează starea/faction-ul și vizibilitatea copilului, menținându-l sincronizat cu părintele. |
| 14 | 12 | Urmărește o țintă X/Y cu schimbări periodice ale țintei și ale vitezei; se rotește către direcția de deplasare. |
| 15 | 13 | Urmărește ținta X/Y cu accelerație/virare și două mount-uri. |
| 16 | 14 | Urmărește ținta X/Y; are comportament de deplasare și armă proprie. |
| 17 | 15 | Controlează copilul archetype 22 (piciorul stamper): ridicare, coborâre accelerată, impact, camera shake și particule de praf; copilul are health special. |
| 18 | 16 | Controlează copilul archetype 20 ca bombă/încărcătură: îl ține atașat, îl lansează, apoi îl transformă în actor independent și incrementează contorul de inamici. |
| 19 | 17 | Heading forțat la 180° și accelerație scalară progresivă; comportament de obiect/proiectil. |
| 20 | 18 | Heading forțat la 180° și accelerație scalară progresivă; comportament de obiect/proiectil. |

### 2.2. Respawn la marginea ecranului

Pentru archetype-urile în intervalul 3..29, dacă actorul trece de X=+600 sau X=-600:
- dacă a fost creat cu flag-ul de spawn/respawn relevant și nu este copil, poate fi recreat pe partea opusă;
- noua instanță păstrează health-ul curent și health-ul maxim;
- multiplicatorii mount-urilor sunt copiați pentru majoritatea tipurilor, cu excepții explicite pentru archetype 5 și 6;
- actorul vechi și copilul său sunt dezactivați;
- contoarele globale și per tip sunt decrementate pentru actorul părinte.

Acest comportament înseamnă că ieșirea din ecran nu este întotdeauna eliminare: anumite unități reapar din partea opusă.

### 2.3. Observații de reconstrucție AI

- Nu implementați `Field5[100]` în Godot. Definiți câte o structură de stare per tip AI (ex. `PogoState`, `KamikazeState`, `StamperState`).
- Separarea „actor archetype” și „enemy ID” trebuie păstrată explicit în date, sau eliminată printr-un singur ID canonic în noua arhitectură. Nu lăsați conversia +2 împrăștiată în cod.
- Enemy 17/18 sunt cluster-bomb entities, nu nave convenționale. Ar trebui modelate separat ca `Hazard` sau `ProjectileActor`, chiar dacă jocul vechi le numără în pool-ul de enemies.
- Păstrați diferența dintre părinte și copil. Un copil poate avea health, faction, coliziune și viață proprie, dar poate fi exclus din numărătoarea standard a inamicilor.

---

## 3. Catalogul boss-urilor

### 3.1. Modelul runtime

Sunt definite 10 sloturi de tip boss (`bosstypes(1..10)`) și 3 sloturi runtime (`boss(1..3)`). În campania inspectată sunt configurate cinci definiții de boss (ID 1–5).

Fiecare boss:
- are o origine X/Y și un heading;
- are până la 10 componente actor;
- păstrează offset-urile componentelor față de origine;
- poate bloca independent poziția X sau Y a unei componente;
- însumează health-ul componentelor active;
- poate atribui recompensa componentei distruse jucătorului care a primit kill attribution;
- poate declanșa efecte de distrugere în lanț prin `partondeathdestroy`.

### 3.2. Maparea componentelor și graficii

Actor archetype IDs 31–44 sunt componentele boss-urilor. Asset mapping:

| Actor archetype | Grafică | Sursă |
|---:|---|---|
| 31 | Boss 1, componenta principală | `gfx/bosses/boss11.png` |
| 32 | Boss 1, componentă secundară | `gfx/bosses/boss12.png` |
| 33 | Boss 2, componentă centrală | `gfx/bosses/boss21.png` |
| 34 | Boss 2, componentă | `gfx/bosses/boss22.png` |
| 35 | Boss 2, componentă | `gfx/bosses/boss23.png` |
| 36 | Boss 2, componente laterale | `gfx/bosses/boss24.png` |
| 37 | Boss 3, componentă principală | `gfx/bosses/boss31.png` |
| 38 | Boss 3, componentă secundară | `gfx/bosses/boss32.png` |
| 41 | Boss 4 | `gfx/bosses/boss4.png` |
| 42 | Boss 5, componentă principală | `gfx/bosses/boss51.png` |
| 43 | Boss 5, componente intermediare | `gfx/bosses/boss52.png` |
| 44 | Boss 5, componentă finală | `gfx/bosses/boss53.png` |

Boss archetype 32, 34, 35 și 36 folosesc grafici statice prin `actorgraphicpath`; restul folosesc animații `animloopgraphics`.

### 3.3. Boss 1

| Componentă | Archetype | Offset X | Offset Y | HP de bază | Recompensă |
|---:|---:|---:|---:|---:|---:|
| 1 | 31 (boss11) | 0 | 0 | 900 | 5000 |
| 2 | 32 (boss12) | -164 | -2 | 500000? | 2500 |

**Corecție de interpretare:** valoarea din Field20 este recompensa, nu HP. HP-ul componentei 2 trebuie citit din definiția actorului 32: 450. Deci tabelul corect este 900 HP și 450 HP. În `partondeathdestroy`, distrugerea componentei 1 setează relația către componenta 2; verificarea exactă a valorii 1 din matrice indică o regulă de distrugere asociată, iar efectul runtime este procesat de `dobosses()`.

AI Case 1 nu are cod specific în `dobossai()`; boss-ul folosește deplasarea comună a originii și poziționarea componentelor.

### 3.4. Boss 2

| Componentă | Archetype | Offset X | Offset Y | HP de bază | Recompensă |
|---:|---:|---:|---:|---:|---:|
| 1 | 36 (boss24) | -46 | -143 | 2000 | 3000 |
| 2 | 36 (boss24) | 41 | -143 | 2000 | 3000 |
| 3 | 33 (boss21) | 0 | 0 | 1300 | 8000 |
| 4 | 34 (boss22) | -137 | 23 | 600 | nespecificată |
| 5 | 35 (boss23) | 134 | 23 | 600 | nespecificată |

AI Case 2:
- componentele 1 și 2 au propriile state machines;
- se deplasează în jos cu accelerație până la Y=-250;
- la impact declanșează rumble, cameră shake și praf;
- apoi revin către offset-ul inițial;
- actorii sunt tratați ca părți mobile independente.

Componenta 3 are o relație de distrugere în lanț cu componentele 1, 2, 4 și 5, conform tabelului `partondeathdestroy`. Este o structură de boss multipart, nu cinci ținte complet independente.

### 3.5. Boss 3

| Componentă | Archetype | Offset X | Offset Y | HP de bază | Recompensă |
|---:|---:|---:|---:|---:|---:|
| 1 | 37 (boss31) | 0 | 0 | 1350 | 15000 |
| 2 | 38 (boss32) | 0 | -110 | 1000 | 7500 |

AI Case 3:
- componenta 2 se deplasează orizontal între limitele aproximative -145 și +140;
- direcția se inversează la limite;
- unghiurile mount-urilor 2 și 3 ale componentei 1 sunt sincronizate cu unghiul mount-ului 1.

### 3.6. Boss 4

| Componentă | Archetype | Offset X | Offset Y | HP de bază | Recompensă |
|---:|---:|---:|---:|---:|---:|
| 1 | 41 (boss4) | 0 | 0 | 7000 | 20000 |

AI Case 4:
- avansează un timer;
- la fiecare aproximativ 300 unități de timp creează Enemy ID 11 (Energy sphere);
- poziția de spawn este în jurul originii boss-ului, la Y-100;
- heading-ul ales este 270° sau 90°.

### 3.7. Boss 5

| Componentă | Archetype | Offset X | Offset Y | HP de bază | Recompensă |
|---:|---:|---:|---:|---:|---:|
| 1 | 42 (boss51) | 0 | 0 | 8000 | 50000 |
| 2 | 43 (boss52) | -270 | 0 | 6000 | 25000 |
| 3 | 43 (boss52) | -515 | 0 | 6000 | 25000 |
| 4 | 44 (boss53) | -765 | 0 | 5000 | 15000 |

AI Case 5:
- inspectează componentele active în ordine;
- determină componenta activă cu indexul cel mai mare;
- actualizează poziția/offset-ul de referință în funcție de componenta activă;
- reface health-ul componentelor precedente în anumite condiții.

Această logică sugerează o succesiune de distrugere/progresie între segmente, nu doar un boss cu patru părți atacabile simultan. Ordinea exactă de activare și toate tranzițiile trebuie confirmate prin urmărirea completă a matricei `partondeathdestroy` și a stării în runtime.

### 3.8. Interpretarea câmpurilor `bosstype`

Mapare bazată pe utilizarea în `makeboss()` și `dobosses()`:

| Câmp | Semnificație |
|---|---|
| Field0 | Slot activ |
| Field1 | Unghi inițial/orientare preferată pentru amplasarea componentelor |
| Field2 | Boss type ID |
| Field3 / Field4 | Originea X/Y |
| Field5 | Heading-ul curent al originii |
| Field6 / Field7 | Ținta curentă de deplasare X/Y |
| Field8 | Offset vertical aplicat țintei de deplasare |
| Field9 | Viteza de deplasare a originii |
| Field10 | Health total/maxim al componentelor |
| Field11 | Health curent agregat |
| Field12[1..10] | Flag componentă activă |
| Field13[1..10] | Slotul actor runtime al componentei |
| Field14[1..10] | Archetype ID al componentei |
| Field15/Field16[1..10] | Offset X/Y față de origine |
| Field17[1..10] | Inversarea direcției de poziționare/orientare a componentei |
| Field18/Field19[1..10] | Flag-uri care blochează actualizarea poziției X/Y din offset |
| Field20[1..10] | Recompensa la distrugerea componentei |
| Field21[1..20] | Memorie de stare specifică AI-ului boss-ului |

### 3.9. Relații de distrugere între componente

Matricea `partondeathdestroy(boss_type, destroyed_part, target_part)` este consultată când o componentă activă este găsită moartă. Dacă rezultatul este pozitiv, componenta țintă este setată cu health 0, Field35 0 și flag-ul activ este eliminat. Valorile 1 și 2 apar în definiții, dar semantica diferenței dintre cele două moduri nu este încă demonstrată complet.

Nu simplificați acest sistem la „când moare boss-ul, dispar toate părțile”. Recompensele, kill attribution și lanțurile de distrugere sunt gestionate per componentă.

---

## 4. Riscuri și anomalii observate

1. **Sloturile actorilor sunt limitate la 20.** Un boss cu mai multe componente poate concura cu inamicii și actorii secundari pentru aceleași sloturi.
2. **Alocarea forțată poate reutiliza slotul 1.** `makeactor(..., force=1)` poate suprascrie un actor activ dacă pool-ul este plin. Boss parts folosesc `force=0`, dar unele spawn-uri speciale pot cere forțare.
3. **Enemy ID 17 și 18 sunt entități de tip cluster-bomb.** Deși sunt create prin `makeenemy()`, rolul lor nu este același cu al navelor standard.
4. **Health-ul componentelor boss este separat.** Health-ul total este calculat din componentele active, iar reward-ul este acordat pe componentă.
5. **Field5[100] este polimorfic.** În Godot, fiecare archetype AI trebuie să aibă state propriu, cu nume semnificative.
6. **EnemyType Field3 nu trebuie confundat cu HP.** Este valoare de scor/recompensă; HP se află în `actortypes.Field25`.
7. **Boss Case 1 este gol.** Nu există logică de fază dedicată în `dobossai()` pentru boss type 1, dar există mișcare comună a originii și poziționarea părților.

---

## 5. Recomandare de arhitectură Godot 4

### Resurse de date

- `EnemyDefinition.tres`: ID canonic, nume intern, sprite/animation, HP, speed, collision shape, faction, weapon mounts, spawn policy.
- `BossDefinition.tres`: boss ID, componente, offset-uri, HP, recompense, relații de distrugere, movement profile.
- `ActorPartDefinition.tres`: grafică, collision profile, weapon mounts, parent/child role.
- `EnemyAIProfile.gd`: bază tipizată sau interfață pentru state machine.
- `BossAIProfile.gd`: comportament separat pentru fiecare boss.

### Scenă recomandată

```
Enemy (Node2D)
├── Sprite2D / AnimatedSprite2D
├── CollisionShape2D
├── WeaponMounts
└── AIController

Boss (Node2D)
├── BossPart (instanță repetată)
│   ├── Sprite2D
│   ├── CollisionShape2D
│   └── WeaponMounts
├── BossHealthController
└── BossAIController
```

Boss-ul trebuie să fie un controller logic care deține componente actor separate, nu un singur sprite cu un singur health pool.

---

## 6. Ce rămâne neconfirmat

- Numele canonice ale tuturor inamicilor, dincolo de numele asset-urilor.
- Sensul complet al tuturor flag-urilor de spawn din `enemytypetype.Field2`.
- Diferența funcțională dintre valorile 1 și 2 din `partondeathdestroy`.
- Ordinea completă a fazelor boss 5 și toate efectele relațiilor de distrugere.
- Valorile exacte de health/reward pentru componentele care nu au Field20 explicit; acestea trebuie citite din actor archetype și nu pot fi presupuse din tabelul boss.
- Comportamentul dinamic al AI-ului și coliziunilor nu a fost testat în executabil.

Auditul este static și se bazează pe codul Gusto-modificat/decompilat. Nu stabilește că fiecare mecanică este identică cu versiunea originală Mighty Rodent.
