Download and install MySQL
1. Download MySQL Community from this link https://dev.mysql.com/downloads/installer/, by clicking the download button with "2.3M" beside.
2. After clicking the download button, just click "No thanks, just start my download" at the bottom.
3. Open the installer downloaded just now, and choose "Custom" as a setup type. Click Next.
4. Select "MySQL Workbench 8.X.XX", "MySQL Server 8.X.XX" and "Connector/J 8.X.XX" by clicking right arrow in the middle as a Prodcuts to be installed. Click Next.
5. Start the Product installation. 
6. Click Next all the way.
7. Create MySQL root password, and note down the password at a place(have to use it later). Click Next.
8. Click Next,Execute and Finish all the way.

Configuring database
1. Open MySQL Command Line Client.
2. Enter the password created just now.
3. Enter these command:
    CREATE USER 'liveSale'@'localhost' IDENTIFIED BY 'liveSale';
    GRANT ALL PRIVILEGES ON * . * TO 'liveSale'@'localhost';
    CREATE DATABASE livesalesystemdb;
4. Open MySQL Workbench.
5. Click "+" button beside "MySQL Connections".
6. Fill in Connection Name with "LiveSaleSystemDB".
7. Fill in Username with "liveSale".
8. Click "Store in Vault" button beside Password field, and fill in Password with "liveSale".

Database creation and insert data
1. Open the Connection named "LiveSaleSystemDB" created just now.
2. Go to File -> Open SQL Script -> choose this program folder location and open "CreateAndInsertAll.sql" under "ddl" folder.
3. Execute the opened script file by clicking the thunder'⚡' icon on top of the script, also beside save'💾' icon.

Execute Application (requirement: done all the steps above)
1. Open Java IDE or Code Editor for Java
2. Go to src -> application -> App.java
3. Run the file


 

