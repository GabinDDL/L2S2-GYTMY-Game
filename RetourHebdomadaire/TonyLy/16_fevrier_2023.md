# Quoi de neuf ?

* De quoi gérer la view.
* Toujours en WIP!

## GraphicalLauncher.java

* Classe qui lance l'interface graphique
  * Signatures:
    * `public void run();`

## StartMenu.java

* Classe qui affiche le menu de démarrage
  * Signatures
    * `private void initMenu();`
    * `private void initTextField();`
    * `private void initPlayerSettingsButton();`

## SettingsMenu.java

* Classe qui affiche le menu de paramétrage
  * Signatures:
    * `public void initPlayersPanel();`
    * `public JPanel createPlayerPanel(int playerID);`
    * `private void addNameSectionToPanel(JPanel playerPanel, int playerID);`
    * `private void addColorSectionToPanel(JPanel playerPanel, int playerID);`
    * `private void addValidateSectionToPanel(JPanel playerPanel, int playerID);`
    * `private void lockPlayerSettings(int playerID);`
    * `private void disableNameSection(int playerID);`
    * `private JTextField getNameField(int playerID);`
    * `private void disableValidateSection(int playerID);`
    * `public void initPlayer(int playerID);`
    * `private Color getPlayerColorFromPanel(int playerID);`
    * `private void initPlayButtons();`
    * `private void initPlayButtonLabyrinth1D();`
    * `private void initDimensionPickerLabyrinth1D();`
    * `private void startGame1D(int length);`
    * `private void initPlayButtonLabyrinth2D();`
    * `private void initDimensionPickerLabyrinth2D();`
    * `public void startGame2D(int width, int height);`
    * `private void addInputFieldInPanel(InputField inputField, JPanel panel, String instructions);`

## GameData.java

* Classe qui gère les données entrées pour initialiser le jeu
  * Signatures
    * `public int getDimension();`
    * `public int getWidthLabyrinth();`
    * `public int getHeightLabyrinth();`

## LabyrinthController.java

* Interface du Controller
  * Signatures:
    * `public void movePlayer(Player player, Direction direction);`

## LabyrinthControllerImplementation.java

* Classe du Controller
  * Signatures
    * `public void initGame();`
    * `private void initGame1D();`
    * `private void initGame2D();`
    * `public LabyrinthModel getModel();`
    * `public LabyrinthView getView();`
    * `public void movePlayer(Player player, Direction direction);`

## LabyrinthView.java + LabyrinthViewImplementation.java

* Interface pour la view et son implémentation
  * Signatures:
    * void update();`
    * LabyrinthTilePanel getTilePanel();`

## LabyrinthTilePanel.java

* Pour générer un JPanel pour la view représentant le model
  * Signatures:
    * `public void updateTilePanel();`
    * `private void tileLabyrinth(int nbRowsBoard, int nbColsBoard);`
    * `private void tileFloor(int row, int col);`

## Player.java + PlayerImplementation.java

* Interface pour les joueurs et son implémentation
  * Signatures:
    * `public int getX();`
    * `public int getY();`
    * `public Vector2 getCoordinates();`
    * `public String getName();`
    * `public Color getColor();`
    * `public boolean isReady();`
    * `public static boolean areAllPlayersReady(Player... players);`
    * `public void setX(int x);`
    * `public void setY(int y);`
    * `public void setCoordinates(Vector2 coordinates);`
    * `public void setName(String name);`
    * `public void setColor(Color color);`
    * `public void setReady(boolean ready);`
    * `public void move(Direction direction);`

## Toolbox.java

* Classe qui sert de boite à outils
  * Signatures
    * `public static void frameUpdate(JFrame frame, String title);`

## InputField.java

* Interface pour les classes UserInputField.java et UserInputFieldRange.java
  * Signatures
    * `public boolean isValidInput();`
    * `public String getText();`
    * `public void setText(String text);`
    * `public JTextField getTextField();`

## UserInputField.java

* Classe qui gère les inputs textuels
  * Signatures
    * `public boolean isValidInput();`
    * `public String getText();`
    * `public void setText(String text);`
    * `public JTextField getTextField();`

## UserInputFieldRange.java

* Classe qui gère les inputs textuels numériques
  * Signatures
    * `public boolean isValidInput();`
    * `private boolean isInRangeOfBounds(int value);`
    * `private boolean isOnlyDigits(String input);`
    * `public int getValue();`
