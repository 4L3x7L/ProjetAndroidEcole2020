# ProjetAndroidEcole2020

Application de tracking de cryptomonnaie
API utilisé : coincap ; L’API se met à jour toutes les 30 secondes avec les nouvelle données. L'application recense 100 cryptomonnaies différentes

Affichage de la liste avec un recyclerView et des cardVIew en couleur sombre pour mieux apprécier l’app.

![Alt text](/Screen/list.PNG)

Architecture MVVM sur la liste

Affichage des détails de chaque cryptomonnaie. Malheureusement l’API ne donne pas d’images pour chaque élément. Ni aucune autre API de cryptocurrency sans Authentification avec une API KEY. Et je n’ai pas eu le temps d’implémenter une requête vers une API avec Auth KEY.

![Alt text](/Screen/details.PNG)

Afin d’actualiser les données de l’API l’utilisateur peut swipe vers le bas dans la liste ou dans le détails pour actualiser quand il le souhaite.

![Alt text](/Screen/reloadDetails.PNG)  ![Alt text](/Screen/refreshList.PNG)

Dans le cas où le téléphone n’a pas accès a internet, les données sont stocker dans les sharedpreference. Dans cette instance, un petit toast est affiché pour prévenir l’utilisateur qu’il est en mode hors ligne et donc les donnée affichées ne sont pas à jour.

![Alt text](/Screen/offline.PNG)

Création d’un Singleton et utilisation de Git
