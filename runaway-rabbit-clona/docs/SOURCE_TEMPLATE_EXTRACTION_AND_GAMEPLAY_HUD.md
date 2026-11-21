# Extragerea template-urilor originale și HUD-ul gameplay

## Template-uri extrase

Fișierul `runaway-rabbit-clona/data/source_level_templates.json` conține toate cele 15 template-uri inițializate explicit în `Functions/EntryPoint.bb`, nu o reinterpretare manuală. Pentru fiecare template sunt păstrate:
- ID-ul sursă și intervalul de linii din `EntryPoint.bb`;
- fiecare eveniment identificat și indexul său original;
- valorile câmpurilor `Field0`–`Field30` care sunt atribuite explicit;
- indexurile array-urilor originale (1-based);
- expresia brută și numărul liniei pentru fiecare atribuire, inclusiv expresiile care nu sunt literali.

În total au fost extrase 220 de evenimente și 3.316 atribuiri de câmp. Patru atribuiri folosesc `local6`/`local7`; am verificat că aceste variabile locale nu sunt inițializate nicăieri în `EntryPoint()`, deci valoarea numerică implicită Blitz BASIC este 0. Expresia originală rămâne păstrată în `raw_assignments`, împreună cu valoarea rezolvată.

Numărul de evenimente extrase per template este: 1=11, 2=12, 3=14, 4=16, 5=16, 6=14, 7=15, 8=16, 9=14, 10=16, 11=16, 12=14, 13=16, 14=16, 15=14.

## Loader Godot

`scripts/levels/level_template_library.gd` citește JSON-ul și creează `LevelDefinition` și `LevelEventData` tipizate. Fiecare eveniment păstrează întregul dicționar original în `source_fields`, astfel încât câmpurile a căror semantică nu este încă stabilită nu se pierd.

API:
- `LevelTemplateLibrary.get_template(source_template_id)` încarcă direct unul dintre cele 15 template-uri;
- `LevelTemplateLibrary.get_campaign_level(level_id)` încarcă template-urile-seed în pozițiile de campanie confirmate de `makemorelevels.bb`;
- `LevelTemplateLibrary.get_available_source_templates()` întoarce ID-urile 1–15 disponibile.

Maparea seed: 1→1, 2→2, 3→3, 4→4, 5→5, 6→7, 7→9, 8→11, 9→13, 10→16, 11→19, 12→21, 13→24, 14→27, 15→29. Nivelurile fără template seed întorc momentan `null`; generatorul procedural complet `makemorelevels()` nu este încă portat.

În `LevelDirector`, indexul inițial este 1 (zero-based), deoarece `startlevel.bb` setează `currentevent=1`, iar `dolevels.bb` începe cu evenimentul 2. Ceasul nivelului se oprește la pragul următor dacă acel eveniment așteaptă înfrângerea unui boss.

## HUD original de gameplay

Interfața este definită în `Functions/loadgui.bb` și actualizată în `Functions/dohud.bb`. Scene Godot: `ui/gameplay_hud.tscn`, script: `scripts/ui/gameplay_hud.gd`.

Au fost copiate în `assets/original_gameplay/` resursele originale `p1lives.png`, `p2lives.png`, `hudshield.png`, `hudbombs.png`, `fonts/font01.png` și `fonts/font02.png`.

`scripts/ui/bitmap_text.gd` reconstruiește randarea fontului Blitz3D: două atlasuri 8×8, ordinea caracterelor din `character_set`, tăierea marginilor transparente pe orizontală, spațiu de 0.3 din înălțimea fontului și aliniere centrală. Scenele HUD păstrează elementele ca noduri editabile.

Pozițiile sunt preluate din `loadgui.bb` în coordonate logice 640×480 și scalate la viewport. Sunt prezente ambele zone de jucător; zona Player 2 este ascunsă deoarece prototipul Godot actual rulează doar single-player. Scorul, iconul de vieți și bara de bombe sunt conectate la starea actuală a Player-ului. Scutul și indicatorul laser rămân ascunse până când subsistemele lor sunt implementate.

## Limite și pașii următori

- Datele brute ale template-urilor sunt extrase; generarea procedurală a nivelurilor 6, 8, 10, 12, 14, 15, 17, 18, 20, 22, 23, 25, 26 și 28 trebuie încă portată.
- `Field20`, `Field21`, `Field27`, `Field29` și `Field30` rămân păstrate fără interpretări speculative.
- Tipurile de inamici și boss-uri nu sunt încă executate de `LevelDirector`; este necesară portarea catalogului `enemytypes`, `actortypes` și `bosstypes`.
- HUD-ul original a fost reconstruit din resursele sursă, dar nu a fost încă validat vizual în Godot 4.7.
- Importul PNG-urilor este generat automat de Godot la prima deschidere a proiectului.