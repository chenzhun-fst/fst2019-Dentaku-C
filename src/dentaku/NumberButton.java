package dentaku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *  クラスNumberButtonは、数字ボタンの機能を提供する。
 */
public class NumberButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;

	private DentakuFrame	dentaku;	// 数字ボタンを収容する電卓フレーム

	/**
	 * NumberButtonクラスのコンストラクタ
	 * JButtonクラスのコンストラクタを呼び出し、パラメタで指定された数値をボタンに表示する。
	 * 
	 * @param keyTop: 数字ボタンに表示する数字
	 * @param dentaku: 数字ボタンを収容する電卓フレーム
	 */
	public NumberButton(String keyTop, DentakuFrame dentaku) {
		super(keyTop);
		this.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 32));
		this.addActionListener(this);
		this.dentaku = dentaku;
	}

	/**
	 * 数字ボタンが押下されると呼び出されアクションイベント処理メソッド。
	 * ボタン名「割り当てた数（0～9）」を取り出し、電卓フレームの表示テキストフィールドに追加する。
	 * 
	 * @param evt: 押下されたボタンのイベント
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		String keyNumber = this.getText();
		dentaku.appendResult(keyNumber);
	}
}
