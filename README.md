# SERVER
Server will read players data from a text file and store it to the database according to the club.
Every club has a playerlist and all the players of the database will be divided into them.
Server needs to be started at a specific port.
And password will be set for every club. Default password is "admin".
Club name and password will be mapped together using a HashMap to validate credentials.
Server will also contain a map of club name and its corresponding networkUtil.
Once a client connects to the server with valid credentials, server will create a networkUtil for that and map that to that client's club name.
And will start a read thread for that client.
For each client server will create a network util and a read thread.

# CLIENT
# LOGIN FORM
<h1>First of all login form will be shown to the user.
To enter into the player database system user have to enter valid club name and password.
When user hit the login button the client will connect to server using the ip address and port of the server.
The the club name and password given by user will go to the server. Server will validate the credentials.
If the club name and password is valid then server will sent the corresponding club object to the client.
Also the auction player list will be sent to client as well.
And the user will prompt to the main menu. If the credentials are invalid then error will be shown to user.
Once the user has made it to the main menu, he can now do operations on the players of that club.</h1>
<img src="Screenshots/LoginForm.png" width="400">

# MAIN MENU  
There are 4 options in the menu. Players, Club, Transfer and Log out.

# PLAYERS
In the players menu all the players of the club will be shown.
Under  the players list there is feature of filtering the list according to different attributes like player name,country,position,salary etc.
On clicking a players name, player's card with full details will be show to the user.

# CLUB
In the club menu there is some info of the club like total yearly salary,country wise player count.

# TRANSFER
In the transfer menu users can change the player list of the club by selling/buying.

# SELL
To sell a player user need to choose from available players in the club and set a price tag for that player(Price must be a valid double number).
Once the user hit the sell button price will be set in the Player object. The club name and the player object will be sent to the server.
Server will remove the player from that club in the database and will add the player in auction player list. Then server will send the changed club to the client.
Also server will send the new auction player list to every client.
# BUY
To buy a player user needs to go to buy menu. There is the list of players on auction. User needs to choose a player from that.
Then the player's details will be shown. Under that players price will be shown. Users need to click the price to buy player.
Once the user do this the club name and the player object will be sent to server like before.
And server will remove the player form auction and add the player to that club and send the changed club to the client.
The new auction player list to every client as well.

# LOG OUT
If the user wishes get out of the the database system he needs to click log out button.