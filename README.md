# Projet d'application Android
Voici un projet fait dans le cours d'Application Android au Cégep de Saint-Jérôme.Tout les informations de l'application sont fictives.Le travail a été fait avec <a href="https://github.com/fouki49" target="_blank">@Benjamin Lemire</a> et <a href="https://github.com/DappySorrow" target="_blank">@Jean-Simon Barbeau</a>.Il y a une section de billet avec des détails , des passerelles et des réseaux.Toutes les informations sont appeler par un API pour ensuite être afficher dans l'application Android.Voici quelques captures d'écran de l'application.

# <h1 align="center">Le loading screen</h1>

Ici nous avons simulé un loading screen pour , si au besoin , permettre a l'application de bien loader les composantes nécessaires au bon fonctionnement de l'application.


![loadingScreen](https://github.com/PikminJaune/ProjetAppAndroid/assets/71794298/90927cb6-7b1a-4916-8ce8-dea21623c1f2)

# <h1 align="center">Les billets</h1>

Une fois l'application ouverte , la première page est celle des billets.Les billets sont générés par l'API et affichés ici.On peux scroller pour tout les voir et à droite on voit leurs états et si ils sont ouverts ou fermés.On peut double taper sur l'écran pour ouvrir l'information du billet et changer son état et son ouverture.Une fois changer ça renvoie une requête a l'API pour faire le changement.

![billets](https://github.com/PikminJaune/ProjetAppAndroid/assets/71794298/a522021d-2493-4049-be05-0a2b4be59b29)

On peut voir les détails du billet.En cliquant sur le bouton a droite turquoise , ça nous ouvre google map et nous dirige sur l'endroit où est le billet , dans ce cas en chine.Le bouton "résoudre" ferme le billet et le bouton "installer" ouvre une page pour scannerun doe QR.Les passerelles se retrouve <a href="https://labs.andromia.science/tenretni" target="_blank">ici</a> pour en ajouter une a un billet.

![detailsBillets](https://github.com/PikminJaune/ProjetAppAndroid/assets/71794298/973bd3a9-84cc-43ec-b0a0-4dc6f5d3462e)
![scannerBillet](https://github.com/PikminJaune/ProjetAppAndroid/assets/71794298/cf153887-7c79-4c03-9c57-45ae722cafe6)

*À noter que le scan de code QR est en mode dev dans ce screenshot*

# <h1 align="center">Les passerelles</h1>

On voit la latence , le download et upload.

![passerelles](https://github.com/PikminJaune/ProjetAppAndroid/assets/71794298/9e19e25c-51d4-47bb-a06a-22d4eb185239)


En double cliquant sur une passerelle , on ouvre la page de détails.Plusieurs informations provenant de l'API est affichées.Les couleurs au millieu de l'écran est en réalité un string recu dans l'API et pour chaque 6 caractère une couleur est créer, les 2 premiers et 2 derniers caractères reste en string.Le bouton "redémarrer" met le status "hors ligne" et aucune informations n'est alors afficher puisqu'il est "hors ligne".

![detailsPasserelles](https://github.com/PikminJaune/ProjetAppAndroid/assets/71794298/13871381-2fe2-478e-b992-9ddad3102ef7)
![redemarrerPasserelle](https://github.com/PikminJaune/ProjetAppAndroid/assets/71794298/2b89985b-61b5-4b70-96cc-da46546421b7)

# <h1 align="center">Les réseaux</h1>

Le dernier onglet est une simulation de réseau.

![reseau](https://github.com/PikminJaune/ProjetAppAndroid/assets/71794298/50b8a975-2599-402f-aa74-94454d47a705)


Ce projet nous a surtout appris à proposer plusieurs onglets sur une même application , à utiliser un API dans toute l'application , créer un loading screen , utiliser des code QR et surtout faire du design sur l'app puisque dans la technique il n'y a pas vraimentde c¸oté design , mais avec ce projet je crois que c'est plutôt beau.🙃
