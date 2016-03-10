package chuangbang.view;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class RoundRectImageView extends ImageView{

	public RoundRectImageView(Context context) {
		super(context);
	}

	public RoundRectImageView(Context context, AttributeSet attrs) {
		super(context, attrs,0);
		
	}
	public RoundRectImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public void setCircleImageBitmap(Bitmap bm) {
		setImageBitmap(getCircleBitmap(bm));
	}
	/**
	 * 
	 * @param dra
	 * @return
	 */

	private Bitmap getCircleBitmap(Bitmap bitmap) {
		// 1. ׼���հ׵ġ���ԭͼ�ߴ���ͬ��Bitmap��Ϊ����
		Bitmap background = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		// 2. ��ݱ�����������
		Canvas canvas = new Canvas(background);
		// 3. ���ԭͼ�õ�BitmapShader
		BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP,
				TileMode.CLAMP);
		// 4. ׼������(Paint)��������BitmapShader
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);
		
		// 5. ��Բ��
		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
				bitmap.getHeight()/2, paint);
		Log.i("width", bitmap.getWidth() / 2+"");
		// 6. ������ı���Bitmap����
		return background;
	}







}
