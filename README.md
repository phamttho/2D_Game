Jeu d'Aventure 2D en Java

Aperçu

Ceci est un jeu d'aventure 2D développé en utilisant Java Swing. Le joueur guide un personnage principal à travers une carte, rencontre des ennemis en mouvement et répond à des questions de quiz pour progresser dans le jeu. Le jeu propose des mouvements d'ennemis dynamiques, la détection des collisions et des quiz interactifs pour défier le joueur.

Fonctionnalités

Mouvement du : Utilisez les flèches du clavier pour déplacer le personnage principal dans les quatre directions.
IA d'Ennemis Dynamiques : Les ennemis se déplacent aléatoirement sur la carte, créant un environnement stimulant pour le joueur.
Détection des Collisions : Le personnage principal peut entrer en collision avec des ennemis, déclenchant des défis de quiz interactifs.
Quiz Interactifs : Répondez correctement aux questions de quiz pour passer les ennemis et continuer votre voyage.
Transitions de Carte : Naviguez à travers différentes cartes avec des dispositions et des défis uniques.

Commencer

Prérequis
Java Development Kit (JDK) 8 ou supérieur
Un Environnement de Développement Intégré (IDE) tel que IntelliJ IDEA, Eclipse ou NetBeans
Installation
Clonez le dépôt ou téléchargez le code source.

bash

Copy code

git clone https://github.com/phamttho/2D-Game.git

cd 2D-Game

Ouvrez le projet dans votre IDE préféré.

Assurez-vous que les fichiers d'image (par exemple, carrot_soldier.png) sont placés dans le répertoire correct tel que référencé dans le code.

Lancer le Jeu
Compilez les fichiers Java.

Exécutez la classe BattleGame pour démarrer le jeu.

Comment Jouer

Mouvement : Utilisez les touches fléchées (Haut, Bas, Gauche, Droite) pour déplacer le personnage principal.
Rencontrer des Ennemis : Lorsque vous entrez en collision avec un ennemi, une question de quiz apparaîtra. Répondez correctement pour passer l'ennemi.
Quiz : Lisez la question et sélectionnez la bonne réponse parmi les options proposées. Si vous choisissez la mauvaise réponse, le personnage principal reste à la même position.
Navigation sur la Carte : Explorez la carte, évitez les ennemis et trouvez la porte pour passer à la carte suivante.
Structure du Code
BattleGame.java : Classe principale pour initialiser et exécuter le jeu.
GamePanel.java : Gère le rendu et le mouvement du personnage sur le panneau de jeu.
Map.java : Représente la carte du jeu et gère les informations des tuiles.
Character.java : Représente le personnage principal et gère le mouvement et la détection des collisions.
GamePanel2.java : Gère la deuxième carte du jeu.
Map2.java : Représente la deuxième carte du jeu.

Contribuer
Les contributions sont les bienvenues ! Veuillez forker le dépôt et soumettre une pull request pour examen.

Licence



