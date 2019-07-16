package dentaku;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * クラスDentakuFrameは、電卓ウィンドを表示し、利用者に電卓機能を提供する。
 * 利用者は、電卓ウィンドに表示された数値、演算、クリアのボタンをマウスで操作することで、
 * 電卓機能を利用できる。
 * 
 * @author FST
 * @version 1
 */
public class DentakuFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BorderLayout borderLayout;
	private JTextField result; 			// 計算結果の表示テキストフィールド
	private BigDecimal stackedValue;	// 演算子ボタンを押す前にテキストフィールドにあった値
	private MathContext mc;				// BigDecimalの計算精度指定(16桁,銀行丸め)
	private boolean isStacked = false; 	// stackedValueの数値入力判定
	private boolean afterCalc = false;	// 演算子ボタン押下判定
	private String currentOp = "";		// 押下した演算子ボタン名
	
	/** 
	 * クラスDentakuFrameのコンストラクタ。
	 * 電卓のフレームを生成し、数字ボタン、演算ボタン、クリアボタンを配置する。
	 */
	public DentakuFrame() {
		mc = new MathContext(17, RoundingMode.HALF_EVEN);
		stackedValue = BigDecimal.ZERO;

		// 電卓のプレームを生成する
		contentPane = new JPanel();
		borderLayout = new BorderLayout();
		result = new JTextField("");

		contentPane.setLayout(borderLayout);
		this.setSize(new Dimension(320, 420));
		this.setTitle("電卓 Ver.1.0");
		this.setContentPane(contentPane);
		result.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 32));
		this.setResizable(false);

		// テキストフィールドを配置する
		contentPane.add(result, BorderLayout.NORTH);

		// ボタンを配置するパネルを用意する。4行4列のGridLayoutにする。
		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new GridLayout(4, 4));
		contentPane.add(keyPanel, BorderLayout.CENTER);

		// 数値ボタンと演算ボタンをパネルに配置する
		keyPanel.add(new NumberButton("7",this), 0);
		keyPanel.add(new NumberButton("8",this), 1);
		keyPanel.add(new NumberButton("9",this), 2);
		keyPanel.add(new CalcButton("÷",this), 3);
		keyPanel.add(new NumberButton("4",this), 4);
		keyPanel.add(new NumberButton("5",this), 5);
		keyPanel.add(new NumberButton("6",this), 6);
		keyPanel.add(new CalcButton("×",this), 7);
		keyPanel.add(new NumberButton("1",this), 8);
		keyPanel.add(new NumberButton("2",this), 9);
		keyPanel.add(new NumberButton("3",this), 10);
		keyPanel.add(new CalcButton("－",this), 11);
		keyPanel.add(new NumberButton("0",this), 12);
		keyPanel.add(new NumberButton(".",this), 13);
		keyPanel.add(new CalcButton("＋",this), 14);
		keyPanel.add(new CalcButton("＝",this), 15);

		// クリアボタン(Ｃ)を配置する
		contentPane.add(new ClearButton(this), BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	/**
	 * 表示テキストフィールドに引数の文字列をつなげる。
	 * クラスNumberButtonのメソッドから呼び出される。
	 * 演算対象数値は16桁で、16桁を超過した場合、引数の文字は無視する。
	 * @param c: 押下された数値ボタンの数値
	 */
	public void appendResult(String c) {
		// 演算子ボタン押下の判定。trueならば、演算子ボタンを押した直後。
		if (afterCalc){
			// 押したボタンの数値を設定する（表示テキストフィールドはいったんクリアする）
			result.setText(c);
			afterCalc = false;
		}else if (result.getText().length() < 16) {
			// 16桁未満ならば文字列を連結する。超過した場合、引数の文字は無視する。
			if (c.equals( ".") ){
				// 押したボタンが小数点「.」かつ、
				// 表示テキストフィールドに「.」が存在しなければ連結する。存在する場合は無視する
				if (result.getText().indexOf(".")< 0) {
					if (result.getText().isEmpty())
						result.setText(result.getText() + "0");
					result.setText(result.getText() + c);
				}
			}else {
				result.setText(result.getText() + c);
			}
		}
	}
	
	/**
	 * 演算ボタンのアクションイベント処理から呼び出されるメソッド。
	 * 以前に演算ボタンが押されていたら、以前の演算処理を行う。
	 * その後、今回、押下された演算を記録する。
	 * 
	 * @param operation: 演算子
	 */
	public void calc(String operation) {
		String	currentText = result.getText();
		
		try {
			if (isStacked) {
				// 以前に演算子ボタンが押されたのなら計算結果を出す
				BigDecimal resultValue = new BigDecimal(currentText);
				// 演算子に応じて計算する
				if (currentOp.equals("＋"))
					stackedValue = stackedValue.add(resultValue, mc);
				else if (currentOp.equals("－"))
					stackedValue = stackedValue.subtract(resultValue, mc);
				else if (currentOp.equals("×"))
					stackedValue = stackedValue.multiply(resultValue, mc);
				else if (currentOp.equals("÷"))
					stackedValue = stackedValue.divide(resultValue, mc);
				
				// 計算結果をテキストフィールドに設定
				if (stackedValue.abs().compareTo(new BigDecimal("1E+17")) < 0
					&& 0 < stackedValue.abs().compareTo(new BigDecimal("1E-6"))) {
					result.setText(stackedValue.stripTrailingZeros().toPlainString());
				} else {
					result.setText(stackedValue.stripTrailingZeros().toString());
				}
			}

			// 表示テキストフィールドに数字文字列があれば、数値と押されたボタンの演算子を保存する。
			// 表示テキストフィールドが空ならば、操作を無視する。
			currentOp = operation;
			currentText = result.getText();
			if(!currentText.isEmpty()){
				stackedValue = new BigDecimal(currentText);
				afterCalc = true;
				if (currentOp.equals("＝"))
					isStacked = false;
				else
					isStacked = true;
			}
			
		} catch (NumberFormatException e) {
			// 非数値入力
			clear();
			result.setText("不正な値です");
			afterCalc = true;
		} catch (ArithmeticException e) {
			if (new BigDecimal(currentText).equals(BigDecimal.ZERO)) {
				// 0除算
				clear();
				result.setText("ゼロ除算はできません");
				afterCalc = true;
			} else {
				// その他(OverFlow など)
				clear();
				result.setText("Error(OverFlow)");
				afterCalc = true;
			}
		}
	}
	
	/**
	 * クリアボタンのアクションイベント処理から呼び出されるメソッド。。
	 * 表示テキストフィールド、数値入力判定、演算子ボタン押下判定を初期化する。
	 */
	public void clear() {
		stackedValue = BigDecimal.ZERO;
		result.setText("");
		afterCalc = false;
		isStacked = false;
	}

}
