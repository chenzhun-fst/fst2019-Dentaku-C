package dentaku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *  クラスClearButtonは、電卓のクリアボタン「C」の機能を提供する。 
 */
public class ClearButton extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private DentakuFrame	dentaku;	// クリアボタンを収容する電卓フレーム
	
	/**
	 * クラスClearButtonのコンストラクタ。
	 * JButtonクラスのコンストラクタを呼び出し、クリア「C」をボタンに表示する。
	 */
	public ClearButton(DentakuFrame dentaku) {
		super("Ｃ");
		this.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 32));
		this.addActionListener(this);
		this.dentaku = dentaku;
	}

	/**
	 * クリアボタン「Ｃ」が押下されると呼び出されるアクションイベント処理メソッド。
	 * 電卓フレームのclearメソッドを呼び出し、電卓を初期化する。
	 * 
	 * @param evt: 押下されたボタンのイベント
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		dentaku.clear();
	}
}
