# Eclipseのコンソールからデータベースにアクセスできるプログラム
### 概要
このプログラムはEclipseのコンソールからデータベースにアクセスし、ユーザーや商品、購入履歴などの操作を行うことができます。
JavaとMySQLを使用し、JDBCドライバーを介してデータベースと接続します。

### 技術スタック
- プログラミング言語:  Java
- データベース:  MySQL
- ドライバー:  JDBC Driver

### 機能一覧
1. ユーザーを追加する機能:  新しいユーザーをデータベースに追加します。
2. 商品を追加する機能:  新しい商品をデータベースに追加します。
3. 商品を購入する機能:  ユーザーが指定した商品を購入します。
4. ユーザー検索機能:  名前やIDでユーザーを検索します。
5. 検索した商品から購入したユーザー数を確認する機能:  特定の商品を購入したユーザーの数を表示します。
6. 任意のカテゴリの中に登録商品が何件あるか確認する機能:  商品カテゴリごとの登録商品の数を確認します。
7. 購入日から購入履歴を確認する機能:  購入日を指定して、購入履歴を表示します。


### データベーステーブル構造

1. ユーザーテーブル
テーブル名: users

| カラム名    | データ型       | 制約                 |
|-------------|----------------|----------------------|
| user_id     | INT            | AUTO_INCREMENT, PK   |
| name        | TEXT           | NOT NULL             |
| address     | VARCHAR(255)    | NOT NULL             |
| gender      | CHAR(1)        | NOT NULL             |

SQLクエリ:  
CREATE TABLE users (  
  user_id INT AUTO_INCREMENT NOT NULL,  
  name TEXT NOT NULL,  
  address VARCHAR(255) NOT NULL,  
  gender CHAR(1) NOT NULL,  
  PRIMARY KEY (user_id)  
);

2. 商品カテゴリテーブル
テーブル名: goods_categories

| カラム名            | データ型       | 制約                 |
|---------------------|----------------|----------------------|
| goods_category_id    | INT            | AUTO_INCREMENT, PK   |
| goods_category_name  | TEXT           | NOT NULL             |

SQLクエリ:  
CREATE TABLE goods_categories (  
  goods_category_id INT AUTO_INCREMENT NOT NULL,  
  goods_category_name TEXT NOT NULL,  
  PRIMARY KEY (goods_category_id)  
);

3. 商品テーブル
テーブル名: goods

| カラム名        | データ型       | 制約                     |
|-----------------|----------------|--------------------------|
| goods_id        | INT            | AUTO_INCREMENT, PK       |
| goods_name      | TEXT           | NOT NULL                 |
| goods_category_id | INT           | NOT NULL, FK (goods_categories.goods_category_id) |
| goods_price     | INT            | NOT NULL                 |

SQLクエリ:  
CREATE TABLE goods (  
  goods_id INT AUTO_INCREMENT NOT NULL,  
  goods_name TEXT NOT NULL,  
  goods_category_id INT NOT NULL,  
  goods_price INT NOT NULL,  
  PRIMARY KEY (goods_id),  
  FOREIGN KEY (goods_category_id) REFERENCES goods_categories(goods_category_id)  
);

4. 商品購入テーブル
テーブル名: purchase_goods

| カラム名         | データ型       | 制約                     |
|------------------|----------------|--------------------------|
| purchase_id      | INT            | AUTO_INCREMENT, PK       |
| goods_id         | INT            | NOT NULL, FK (goods.goods_id) |
| user_id          | INT            | NOT NULL, FK (users.user_id) |
| purchase_number  | INT            | NOT NULL                 |
| purchase_date    | DATE           | NOT NULL                 |

SQLクエリ:  
CREATE TABLE purchase_goods (  
  purchase_id INT AUTO_INCREMENT NOT NULL,  
  goods_id INT NOT NULL,  
  user_id INT NOT NULL,  
  purchase_number INT NOT NULL,  
  purchase_date DATE NOT NULL,  
  PRIMARY KEY (purchase_id),  
  FOREIGN KEY (goods_id) REFERENCES goods(goods_id),  
  FOREIGN KEY (user_id) REFERENCES users(user_id)  
);
