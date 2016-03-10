package chuangbang.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 这是自定义进驻孵化器TextView,以此来设置行间距和字间距
 * 
 * @author Administrator
 * 
 */
public class MyTextView extends TextView {
	private String content;
	private int width;
	// 画笔
	private Paint paint;
	// 字间距
	private int xPadding;
	// 行间距
	private int yPadding;
	// 文本高度
	private int textHeight;
	// 比较小的字间距
	private int xPaddingMin;
	int count;
	// 记录每个字的二维数组
	int[][] position;

	public MyTextView(Context context) {
		super(context);
		init();
	}

	public MyTextView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init();
	}

	public MyTextView(Context context, AttributeSet attributeSet, int defStyle) {
		super(context, attributeSet, defStyle);
		init();
	}

	public void setText(String str) {
		width = this.getWidth();
		getpositions(str);
		// 重新画控件
		this.invalidate();
	}

	private void init() {
		// 创建画笔
		paint = new Paint();
		paint.setColor(Color.parseColor("#333333"));
		// 设置字体样式
		paint.setTypeface(Typeface.DEFAULT);
		paint.setTextSize(dip2px(this.getContext(), 14f));
		// 得到系统默认字体属性
		Paint.FontMetrics fontMetrics = paint.getFontMetrics();
		// 获取字体高度
		textHeight = (int) (Math.ceil(fontMetrics.ascent - fontMetrics.top) + 2);
		// 字间距
		xPadding = dip2px(this.getContext(), 4f);
		// 行间距
		yPadding = dip2px(this.getContext(), 10f);
		// 比较小的字间距
		xPaddingMin = dip2px(this.getContext(), 2f);
	}

	/**
	 * 重新画
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!TextUtils.isEmpty(content)) {
			for (int i = 0; i < count; i++) {
				// 绘制文本
				canvas.drawText(String.valueOf(content.charAt(i)),
						position[i][0], position[i][1], paint);
			}
		}
	}

	/**
	 * 在代码中使用dp的方法（因为代码中直接用数字表示的是像素）
	 * 
	 * @param context
	 * @param dip
	 * @return
	 */
	private static int dip2px(Context context, float dip) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/**
	 * 获取positions
	 * 
	 * @param str
	 */
	private void getpositions(String content) {
		this.content = content;
		char cr;
		// 输入点x的坐标
		int x = 0;
		// 当前行数
		int lineNum = 1;
		count = content.length();
		// 初始化字体位置数组
		position = new int[count][2];
		for (int i = 0; i < count; i++) {
			cr = content.charAt(i);
			String str = String.valueOf(cr);
			// 计算文字所在矩形，可以得到宽高
			// 根据画笔获得每一个字符的显示的rect 就是包围框（获得字符宽度）
			Rect rect = new Rect();
			paint.getTextBounds(str, 0, 1, rect);
			int strwidth = rect.width();
			// 对有些标点做些处理
			if (str.equals("<<") || str.equals("(")) {
				strwidth += xPaddingMin * 2;
			}
			// 当前行的宽度
			float textWidth = strwidth;
			// 没画字前预判看是否会出界
			x += textWidth;
			// 出界则换行
			if (x > width) {
				lineNum++;
			} else {
				// 回到预判前位置
				x -= textWidth;
			}
			// 并且记录每一个字的位置
			position[i][0] = x;
			position[i][1] = textHeight * lineNum + yPadding * (lineNum - 1);
			// 判断是否是数字还是字母 （数字和字母应该紧凑点）
			// 每次输入完毕 进入下一个输入位置准备就绪
			if (isNumberOrLetters(str)) {
				x += textWidth + xPaddingMin;
			} else {
				x += textWidth + xPadding;
			}
		}
		// 根据所画的内容设置控件的高度
		this.setHeight((textHeight + yPadding) * lineNum);
	}

	/**
	 * 判断是否是字母或者数字
	 * 
	 * @param str
	 * @return
	 */
	private boolean isNumberOrLetters(String str) {
		String regEx="^[A-Za-z0-9_]+$";
	    Pattern p=Pattern.compile(regEx);
	    Matcher m=p.matcher(str);
	    return m.matches();
	}
}
