# MariaDBのインストール等 <img src="illustrain09-tori7.png" width="100">  

### ☆MariaDB(安定版 10.1)のインストール方法 
https://server.etutsplus.com/centos-7-mariadb-install-and-mysql-secure-installation/

### ☆外部接続を有効にしDBを利用する方法
1.サーバー上のファイアウォールの除外設定にMariaDBを追加。<br>
2.フルコントロール権限を与えたユーザーの作成。<br>

・MariaDBの外部接続を有効にする方法<br>
https://kagasu.hatenablog.com/entry/20160128/1453948015<br>
・MariaDB(MySQL)の基本的なユーザー系コマンド<br>
https://qiita.com/hishigataBOZE/items/00e4522a945399e9bf96<br>　　

### ☆基本操作
・テーブル管理、バックアップ等の基本操作<br>
https://server.etutsplus.com/how-to-use-mariadb/<br>
・テーブル構造を変更する方法<br>
https://techacademy.jp/magazine/5199<br>  
  
### データベースの内容  
データベース名minor  
  
テーブル名major  
カラム名num url com t g  
(nは無し)

テーブル名movies  
カラム名num url com t g n  
  
numはint型で番号  
urlはstring型で映画のURL  
comはstring型で映画の一言コメント  
tはstring型でTSUTAYAの映画のレンタルのURL  
gはstring型でGEOの映画のレンタルのURL  
nはstring型でNetflixの映画のURL  
