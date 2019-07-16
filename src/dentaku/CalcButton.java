package dentaku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** 
 * クラスCalcButtonは、演算子ボタンの機能を提供する。 
 */
public class CalcButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;

	private DentakuFrame	dentaku;	// 演算ボタンを収容する電卓フレーム
	
	/**
	 * クラスCalcButtonのコンストラクタ。
	 * JButtonクラスのコンストラクタを呼び出し、パラメタで指定された演算子をボタンに表示する。
	 * 
	 * @param op: 演算ボタンに表示する演算表記
	 * @param dentaku: 演算ボタンを収容する電卓フレーム
	 */
	public CalcButton(String op, DentakuFrame dentaku) {
		super(op);
		this.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 32));
		this.addActionListener(this);	// アクションイベントのリスナを設定
		this.dentaku = dentaku;
	}

	/**
	 * 演算ボタンが押下されると呼び出されるアクションイベント処理メソッド。
	 * 電卓フレームのcalcメソッドを呼び出し、演算ボタンに応じた演算を行う。
	 * 
	 * @param evt: 押下されたボタンのイベント
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		dentaku.calc(this.getText());
	}
}
