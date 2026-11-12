# TabTab TV

App IPTV Android (mobile + Android TV) écrite de zéro en Kotlin + Jetpack Compose,
qui parle le protocole **Xtream Codes** standard (`player_api.php`) — compatible
avec n'importe quel panneau Xtream Codes auquel tu as légitimement accès.

## Obtenir l'APK sans Android Studio (GitHub Actions)

1. Crée un dépôt GitHub (public ou privé) et pousse tout ce dossier dedans.
2. Va dans l'onglet **Actions** du dépôt — le workflow « Build APK » se lance
   automatiquement au push (ou lance-le manuellement via "Run workflow").
3. Une fois terminé (quelques minutes), ouvre le run et télécharge l'artefact
   **TabTabTV-debug-apk** dans la section "Artifacts" — c'est l'APK installable
   (`app-debug.apk`).
4. Transfère-le sur ton téléphone/TV et installe-le (autorise les sources
   inconnues si demandé).

C'est un APK de debug, non signé pour release — suffisant pour tester.
Pour une version signée release, il faudra ajouter un keystore et étendre
le workflow (`assembleRelease` + signature).

## Ouvrir le projet (si tu récupères Android Studio un jour)

1. Ouvre ce dossier dans Android Studio (Hedgehog ou plus récent).
2. Android Studio génère automatiquement le wrapper Gradle au premier sync.
3. Lance sur un appareil mobile ou un émulateur Android TV.

## Architecture

- `data/api` — interface Retrofit `XtreamApi` (login, catégories, flux live/VOD/séries, EPG)
  + `StreamUrlBuilder` pour les URLs de lecture directe.
- `data/model` — modèles de données alignés sur les réponses Xtream Codes.
- `data/repo` — `ContentRepository`, façade au-dessus de l'API pour les écrans.
- `data/session` — `SessionManager`, stocke les identifiants du panneau via DataStore
  (saisis par l'utilisateur au login — rien n'est codé en dur dans l'APK).
- `ui/screens` — écrans Compose : connexion, accueil à onglets (Live/VOD/Séries).
- `player` — lecture vidéo via Media3/ExoPlayer (support HLS pour le live).
- `AppState.kt` — petite DI manuelle, construit le repository à partir de la session active.

## Différences volontaires avec l'app de référence analysée

- Pas d'URL de panneau chiffrée et embarquée : l'utilisateur saisit son propre
  serveur au login.
- Pas de couche de licence/anti-tamper native : ce projet est à toi, pas revendu.
- Pas de VPN intégré (non demandé pour ce projet).

## À compléter

- Écran de détail série (sélection saison/épisode) — actuellement un no-op dans la nav.
- Gestion d'erreurs réseau plus fine (distinguer panneau injoignable / identifiants invalides).
- EPG affichée dans l'UI (le endpoint `get_short_epg` est déjà câblé côté repository).
- Tests unitaires.
