package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

	//機能選択
	public static void main(String[] args) {
		Main test = new Main();
		while(true) {
			System.out.println("1 → ユーザー追加, 2 → 商品追加, 3 → 商品購入, 4 → ユーザー検索, 5 → 購入ユーザー数, 6 → カテゴリー登録商品数, 7 → 購入履歴");
			try {
				Scanner sc = new Scanner(System.in);
				String select = sc.next();
				int intSelect = Integer.parseInt(select);
				if(intSelect <= 0 || intSelect >= 8) {
					System.out.println("【エラー】入力できるのは1~7のどれかです");
				} else if(intSelect == 1) {
					test.insertUser();
				} else if(intSelect == 2) {
					test.insertGoods();
				} else if(intSelect == 3) {
					test.purchaseGoods();
				} else if(intSelect == 4) {
					test.searchUser();
				} else if(intSelect == 5) {
					test.purchaseUserNumber();
				} else if(intSelect == 6) {
					test.categoryGoodsNumber();
				} else if(intSelect == 7) {
					test.purchaseHistory();
				} else {
					break;
				}
			} catch(Exception e) {
				System.out.println("【エラー】数字を入力してください");
			}
			break;
		}
	}

	//ユーザー追加
	private void insertUser() {
		//このメソッドで使用する変数宣言
		Connection con = null;
		PreparedStatement pstmt = null;
		String name;
		String address;
		int intGender = 0;
		
		try {
			//SQL接続
			con = getConnection();
			Scanner sc = new Scanner(System.in);
			//文字数が1~11文字以外の場合はループする
			while(true) {
				System.out.println("追加するユーザーの名前を入力して下さい");
				name = sc.next();
				if(name.length() <= 0 || name.length() >= 12) {
					System.out.println("【エラー】入力できるのは1~11文字です");
				} else {
					break;
				}
			}
			while(true) {
				System.out.println("追加するユーザーのメールアドレスを入力して下さい");
				address = sc.next();
				if(name.length() <= 0 || address.length() >= 256) {
					System.out.println("【エラー】入力できるのは1~255文字です");
				} else {
					break;
				}
			}
			//入力された値が1, 2以外の場合はループする
			while(true) {
				System.out.println("追加するユーザーの性別を入力して下さい(男性 → 1, 女性 → 2)");
				try {
					String gender = sc.next();
					intGender = Integer.parseInt(gender);
					if(intGender <= 0 || intGender >= 3) {
						System.out.println("【エラー】入力できるのは1, 2のどちらかです");
					} else {
						break;
					}
				} catch(Exception e) {
					System.out.println("【エラー】数字を入力してください");
				}
			}

			//クエリ実行
			pstmt = con.prepareStatement("insert into users (name, address, gender) values (?, ?, ?)");
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setInt(3, intGender);
			pstmt.execute();

			//エラーチェック
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//商品追加
	private void insertGoods() {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		String goods;
		int intCategoryId = 0;
		int price = 0;
		
		try {
			con = getConnection();
			//商品カテゴリー表示クエリ
			pstmt = con.prepareStatement("select goods_category_id, goods_category_name from goods_categories");
			rs = pstmt.executeQuery();
			Scanner sc = new Scanner(System.in);
			while(true) {
				System.out.println("追加する商品名を入力して下さい");
				goods = sc.next();
				if(goods.length() <= 0 || goods.length() >= 12) {
					System.out.println("【エラー】入力できるのは1~11文字です");
				} else {
					break;
				}
			}
			//商品カテゴリー表示
			while (rs.next()) {
				int id = rs.getInt("goods_category_id");
				String name = rs.getString("goods_category_name");
				System.out.println(id + ", " +name);
			}
			while(true) {
				System.out.println("追加するカテゴリーIDを入力して下さい");
				try {
					String categoryId = sc.next();
					intCategoryId = Integer.parseInt(categoryId);
					if(intCategoryId <= 0 || intCategoryId >= 4) {
						System.out.println("【エラー】入力できるのは1~3のどれかです");
					} else {
						break;
					}
				} catch(Exception e) {
					System.out.println("【エラー】数字を入力してください");
				}
			}
			
			while(true) {
				System.out.println("追加する商品の価格を入力して下さい");
				try {
					price = sc.nextInt();
					if(price <= 0 || price >= 1000000) {
						System.out.println("【エラー】入力できるのは1~1000000までです");
					} else {
						break;
					}
				} catch(Exception e) {
					System.out.println("【エラー】数字を入力してください");
				}
			}

			pStmt = con.prepareStatement("insert into goods (goods_name, goods_category_id, goods_price) values (?, ?, ?)");
			pStmt.setString(1, goods);
			pStmt.setInt(2, intCategoryId);
			pStmt.setInt(3, price);
			pStmt.execute();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null || pStmt != null)
					pstmt.close();
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//商品購入
	private void purchaseGoods() {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pStmt = null;
		PreparedStatement pState = null;
		ResultSet rs = null;
		int intSetUserId = 0;
		int intSetGoodsId = 0;
		int price = 0;

		try {
			con = getConnection();
			//ユーザーID表示
			pstmt = con.prepareStatement("select user_id, name from users");
			rs = pstmt.executeQuery();
			//ArrayList配列宣言
			ArrayList<Integer> users = new ArrayList<>();
			while (rs.next()) {
				int userId = rs.getInt("user_id");
				String userName = rs.getString("name");
				System.out.println(userId + ", " + userName);
				//users配列にuserIdを1つずつ追加
				users.add(userId);
			}

			Scanner sc = new Scanner(System.in);
			while(true) {
				System.out.println("商品を購入するユーザーIDを入力して下さい");
				try {
					String setUserId = sc.next();
					intSetUserId = Integer.parseInt(setUserId);
					//flag変数宣言
					boolean flag = true;
					//入力した値とusers配列内の値が一致した場合にループを抜ける
					for(int i:users) {
						if(i == intSetUserId)
							flag = false;
						break;
					}
					if(flag) {
						System.out.println("【エラー】入力したIDはデータベースにありません");
						continue;
					}
				} catch(Exception e) {
					System.out.println("【エラー】数字を入力してください");
				}
				break;
			}

			//商品ID表示、選択
			pStmt = con.prepareStatement("select goods_id, goods_name from goods");
			rs = pStmt.executeQuery();
			ArrayList<Integer> goods = new ArrayList<>();
			while (rs.next()) {
				int goodsId = rs.getInt("goods_id");
				String goodsName = rs.getString("goods_name");
				System.out.println(goodsId + ", " + goodsName);
				goods.add(goodsId);
			}

			while(true) {
				System.out.println("購入する商品IDを入力して下さい");
				try {
					String setGoodsId = sc.next();
					intSetGoodsId = Integer.parseInt(setGoodsId);
					boolean flag = true;
					for(int i:goods) {
						if(i == intSetGoodsId)
							flag = false;
						break;
					}
					if(flag) {
						System.out.println("【エラー】入力したIDはデータベースにありません");
						continue;
					}
				} catch(Exception e) {
					System.out.println("【エラー】数字を入力してください");
				}
				break;
			}

			while(true) {
				System.out.println("購入する個数を入力して下さい");
				try {
					price = sc.nextInt();
					if(price <= 0 || price >= 1000) {
						System.out.println("【エラー】入力できるのは1~999までです");
					} else {
						break;
					}
				} catch(Exception e) {
					System.out.println("【エラー】数字を入力してください");
				}
			}
			
			//現在の日付を取得
			Date now = new Date();
			//パターンを指定し、SimpleDateFormatのインスタンス"dateFormat"を生成
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			//formatの戻り値をdateに格納
			String date = dateFormat.format(now);

			pState = con.prepareStatement("insert into purchase_goods (user_id, goods_id, purchase_number, purchase_date) values (?, ?, ?, ?)");
			pState.setInt(1, intSetUserId);
			pState.setInt(2, intSetGoodsId);
			pState.setInt(3, price);
			pState.setString(4, date);
			pState.execute();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null || pStmt != null || pState != null)
					pstmt.close();
				pStmt.close();
				pState.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//ユーザー検索
	private void searchUser() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String setUserName;
		
		try {
			con = getConnection();
			Scanner sc = new Scanner(System.in);
			while(true) {
				System.out.println("検索したいユーザー名を入力して下さい");
				setUserName = sc.next();
				if(setUserName.length() <= 0 || setUserName.length() >= 12) {
					System.out.println("【エラー】入力できるのは1~11文字です");
				} else {
					break;
				}
			}

			//ユーザー情報取得
			pstmt = con.prepareStatement("select user_id, name, address, gender from users where name like ?");
			//入力した値の部分一致を取得
			pstmt.setString(1, "%" + setUserName + "%");
			rs = pstmt.executeQuery();
			//ユーザー情報表示 + 1回カウントする
			for(int i=0; i<1; i++) {
				if(rs.next()) {
					int userId = rs.getInt("user_id");
					String userName = rs.getString("name");
					String address = rs.getString("address");
					int gender = rs.getInt("gender");
					System.out.println(userId + ", " + userName + ", " + address + ", " + gender);
				} else {
					System.out.println(setUserName + "というユーザー名はデーターベースにありません");
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//購入ユーザー数
	private void purchaseUserNumber() {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		int intSetGoodsId = 0;

		try {
			con = getConnection();
			pstmt = con.prepareStatement("select goods_id, goods_name, goods_price from goods");
			rs = pstmt.executeQuery();
			ArrayList<Integer> goods = new ArrayList<>();
			while (rs.next()) {
				int goodsId = rs.getInt("goods_id");
				String name = rs.getString("goods_name");
				String price = rs.getString("goods_price");
				System.out.println(goodsId + ", " + name + ", " + price);
				goods.add(goodsId);
			}

			Scanner sc = new Scanner(System.in);
			while(true) {
				System.out.println("検索したい商品IDを入力して下さい");
				try {
					String setGoodsId = sc.next();
					intSetGoodsId = Integer.parseInt(setGoodsId);
					boolean flag = true;
					for(int i:goods) {
						if(i == intSetGoodsId)
							flag = false;
						break;
					}
					if(flag) {
						System.out.println("【エラー】入力したIDはデータベースにありません");
						continue;
					}
				} catch(Exception e) {
					System.out.println("【エラー】数字を入力してください");
				}
				break;
			}

			pStmt = con.prepareStatement("select user_id, purchase_number, purchase_date from purchase_goods where goods_id = ?");
			pStmt.setInt(1, intSetGoodsId);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt("user_id");
				String number = rs.getString("purchase_number");
				String date = rs.getString("purchase_date");
				System.out.println(userId + ", " + number + ", " + date);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null || pStmt != null)
					pstmt.close();
				pStmt.close();	
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//カテゴリー登録商品数
	private void categoryGoodsNumber() {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		int intSetCategoryId = 0;

		try {
			con = getConnection();
			pstmt = con.prepareStatement("select goods_category_id, goods_category_name from goods_categories");
			rs = pstmt.executeQuery();
			ArrayList<Integer> categories = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("goods_category_id");
				String name = rs.getString("goods_category_name");
				System.out.println(id + ", " + name);
				categories.add(id);
			}

			Scanner sc = new Scanner(System.in);
			while(true) {
				System.out.println("検索したいカテゴリーIDを入力して下さい");
				try {
					String setCategoryId = sc.next();
					intSetCategoryId = Integer.parseInt(setCategoryId);
					boolean flag = true;
					for(int i:categories) {
						if(i == intSetCategoryId)
							flag = false;
						break;
					}
					if(flag) {
						System.out.println("【エラー】入力したIDはデータベースにありません");
						continue;
					}
				} catch(Exception e) {
					System.out.println("【エラー】数字を入力してください");
				}
				break;
			}

			pStmt = con.prepareStatement("select goods_id, goods_name, goods_category_id from goods where goods_category_id = ?");
			pStmt.setInt(1, intSetCategoryId);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				int goodsId = rs.getInt("goods_id");
				String goodsName = rs.getString("goods_name");
				String categoryId = rs.getString("goods_category_id");
				System.out.println(goodsId + ", " + goodsName + ", " + categoryId);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null || pStmt != null)
					pstmt.close();
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//購入履歴
	private void purchaseHistory() {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		int intSetDate = 0;

		try {
			con = getConnection();
			pstmt = con.prepareStatement("select purchase_id, purchase_date from purchase_goods");
			rs = pstmt.executeQuery();
			ArrayList<Integer> dates = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("purchase_id");
				Date date = rs.getDate("purchase_date");
				System.out.println(id + ", " + date);
				dates.add(id);
			}

			Scanner sc = new Scanner(System.in);
			while(true) {
				System.out.println("検索したい購入日のIDを入力して下さい");
				try {
					String setDate = sc.next();
					intSetDate = Integer.parseInt(setDate);
					boolean flag = true;
					for(int i:dates) {
						if(i == intSetDate)
							flag = false;
						break;
					}
					if(flag) {
						System.out.println("【エラー】入力したIDはデータベースにありません");
						continue;
					}
				} catch(Exception e) {
					System.out.println("【エラー】数字を入力してください");
				}
				break;
			}

			//購入履歴表示
			pStmt = con.prepareStatement("SELECT * FROM purchase_goods LEFT OUTER JOIN users ON users.user_id = purchase_goods.user_id LEFT OUTER JOIN goods ON goods.goods_id = purchase_goods.goods_id WHERE purchase_id = ?");
			pStmt.setInt(1, intSetDate);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				int purchaseId = rs.getInt("purchase_id");
				String purchaseDate = rs.getString("purchase_date");
				int number = rs.getInt("purchase_number");
				String name = rs.getString("name");
				String address = rs.getString("address");
				int gender = rs.getInt("gender");
				String goodsName = rs.getString("goods_name");
				int categoryId = rs.getInt("goods_category_id");
				int price = rs.getInt("goods_price");
				System.out.println(purchaseId + ", " + purchaseDate + ", 購入数：" + number + ", " + 
						name + ", " + address + ", 性別：" + gender + ", " + goodsName + ", カテゴリID：" + categoryId + ", 金額：" + price);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null || pStmt != null)
					pstmt.close();
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//MySQL接続
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3306/test2";
		String user = "root";
		String pass = "Kanematsu@1220";
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, user, pass);
	}

}
