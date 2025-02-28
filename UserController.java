package jp.co.internous.team2412.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.team2412.model.domain.MstUser;
import jp.co.internous.team2412.model.form.UserForm;
import jp.co.internous.team2412.model.mapper.MstUserMapper;
import jp.co.internous.team2412.model.session.LoginSession;

/**
 * ユーザー登録に関する処理を行うコントローラー
 * @author インターノウス
 *
 */
@Controller
@RequestMapping("/team2412/user")
public class UserController {
		
	/*
	 * フィールド定義
	 */
	
	@Autowired
	private MstUserMapper userMapper;
	@Autowired
	private LoginSession loginSession;
	
	
	/**
	 * 新規ユーザー登録画面を初期表示する。
	 * @param m 画面表示用オブジェクト
	 * @return 新規ユーザー登録画面
	 */
	@RequestMapping("/")
	public String index(Model m) {
	/*
	 * 「04_画面設計書_新規ユーザー登録.pdf」を表示する。
	 * 新規ユーザー登録画面の表示。
	 */
		m.addAttribute("loginSession", loginSession); 
		return "register_user"; 
		
	}
	
	/**
	 * ユーザー名重複チェックを行う
	 * @param f ユーザーフォーム
	 * @return true:重複あり、false:重複なし
	 */
	@PostMapping("/duplicatedUserName")
	@ResponseBody
	public boolean duplicatedUserName(@RequestBody UserForm f) {
		/*
		 * 重複確認ボタンを押下された場合は、以下の処理をおこなう。
		   ユーザーの存在チェック(DBの会員情報マスタテーブルにユーザー名が一致するユーザーが存在しているかを確認)をおこなう。
		   結果を返す。　
		 */
		int count = userMapper.findCountByUserName(f.getUserName());
		if(count>0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * ユーザー情報登録を行う
	 * @param UserForm ユーザーフォーム
	 * @return true:登録成功、false:登録失敗
	 */
	@PostMapping("/register")
	@ResponseBody
	public boolean register(@RequestBody UserForm f) {
		/*
		 *登録ボタンを押下された場合は、入力チェック(【入力チェック】参照)をおこなう。
		 *エラーがない場合は、DBの会員情報マスタテーブルに入力値を登録する。
		 *結果を返す。（登録が成功すればtrue、失敗ならfalseを返す）
		 */
		
		MstUser user= new MstUser();
		user.setUserName(f.getUserName());
		user.setPassword(f.getPassword());
		user.setFamilyName(f.getFamilyName());
		user.setFirstName(f.getFirstName());
		user.setFamilyNameKana(f.getFamilyNameKana());
		user.setFirstNameKana(f.getFirstNameKana());
		user.setGender(f.getGender());
	    int result = userMapper.insert(user); 
	    if(result>0) {
			return true;
		} else {
			return false;
		}
	    
	}
}














