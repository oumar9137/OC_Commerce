OC COMMERCE — VERSION WINDOWS

Démarrer l'application
- Lancez dist/OC_Commerce.exe.
- Gardez le fichier OC_Commerce.exe, le dossier app et le dossier runtime ensemble. L'application apporte son propre environnement Java.
- Pour transmettre cette version, partagez dist/OC_Commerce_Windows.zip et décompressez-le entièrement avant de lancer OC_Commerce.exe.

Base de données
- Cette installation utilise MySQL local : serveur MySQL 8 actif, port 3306, base gestion_commerce.
- La base locale actuelle a été réinitialisée : les données de démonstration ont été retirées. Le compte administrateur et la structure restent.
- Une sauvegarde complète est conservée dans backup_local. Elle contient les anciennes données : ne la transmettez pas à un tiers.

Fonctions de cette version
- Logo OC Commerce sur la connexion, le menu, les fenêtres et l'icône Windows.
- Comptes ADMIN, GESTIONNAIRE et VENDEUR, avec accès aux modules selon le profil.
- Hachage des mots de passe et migration automatique des anciens comptes lors de leur prochaine connexion.
- Ventes et achats transactionnels, avec contrôle du stock au moment de la vente.
- Recherche de stock, alertes colorées au seuil de chaque article et export CSV.
- Thème graphique commun, tableaux plus lisibles et fenêtres de gestion indépendantes.

Distribution à un autre commerce
Le ZIP contient le programme et son environnement Java, mais pas MySQL ni une base configurée pour l'autre commerce. Chaque entreprise doit disposer de sa propre base, de ses comptes et de ses données.
