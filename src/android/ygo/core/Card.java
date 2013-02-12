package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.ygo.utils.Utils;

public class Card implements SelectableItem {
    public static final Bitmap UNKNOWN_CARD = Utils.readBitmapScaleByHeight(Configuration.unknownCard() + ".png", Utils.cardHeight());
    public static final Bitmap CARD_PROTECTOR = Utils.readBitmapScaleByHeight(Configuration.cardProtector() + ".png", Utils.cardHeight());

    private boolean selected = false;

    String id;
    String name;
    String desc;
    CardType type;
    boolean set = false;
    boolean positive = true;

    Bitmap cardPic;
    Bitmap highLight;

    public Card(String id, String name) {
        this(id, name, CardType.NORMAL_MONSTER, true, true);
    }


    public Card(String id, String name, CardType type, boolean positive, boolean set) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.positive = positive;
        this.set = set;
        initCardPic();
    }

    public CardType getType() {
        return type;
    }

    public void flip() {
        set = !set;
    }

    public void open() {
        set = false;
    }

    public void set() {
        set = true;
    }

    public void changePosition() {
        positive = !positive;
    }

    public void positive() {
        positive = true;
    }

    public void negative() {
        positive = false;
    }

    private void initCardPic() {
        int height = Utils.cardHeight();
        try {
            cardPic = Utils.readBitmapScaleByHeight(id + ".png", height);
        } catch (Exception e) {
            cardPic = Bitmap.createBitmap(Utils.cardWidth(), Utils.cardHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(cardPic);
            Paint paint = new Paint();
            Utils.drawBitmapOnCanvas(canvas, UNKNOWN_CARD, paint, Utils.DRAW_POSITION_FIRST, Utils.DRAW_POSITION_FIRST);
            CharSequence cs = longName();
            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(Utils.unitLength() / 10);
            textPaint.setColor(Configuration.fontColor());
            StaticLayout layout = new StaticLayout(cs, textPaint, Utils.cardWidth(), Layout.Alignment.ALIGN_CENTER, 0, 0, true);
            canvas.translate(0, Utils.cardHeight() / 20);
            layout.draw(canvas);
        }
        highLight = highLight();
    }

    private String longName() {
        char[] cs = name.toCharArray();
        int charLen = 0;
        int maxCutFlag = 10;
        int cutIndex = cs.length;
        for(int i=0;i<cs.length;i++) {
            int newLen = charLen + Utils.textPlace(cs[i]);
            if(newLen > maxCutFlag) {
                cutIndex = i;
                break;
            }
            charLen = newLen;
        }
        String nName = name.substring(0, cutIndex);
        if(cutIndex != name.length()) {
            nName += "...";
        }
        return nName;
    }

    @Override
    public Bitmap toBitmap() {
        int height = Utils.cardHeight();
        int width = Utils.cardWidth();
        Bitmap cardBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cardBmp);
        Paint paint = new Paint();
        if (set) {
            Utils.drawBitmapOnCanvas(canvas, CARD_PROTECTOR, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);
        } else {
            Utils.drawBitmapOnCanvas(canvas, cardPic, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);
        }

        if (selected) {
            Utils.drawBitmapOnCanvas(canvas, highLight, paint, Utils.DRAW_POSITION_CENTER, Utils.DRAW_POSITION_FIRST);
        }

        if (!positive) {
            cardBmp = Utils.rotate(cardBmp, 90);
        }
        return cardBmp;
    }

    private Bitmap highLight() {
        int height = Utils.cardHeight();
        int width = Utils.cardWidth();
        Bitmap highLightBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(highLightBmp);
        Paint paint = new Paint();
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(Configuration.highlightColor());
        paint.setStrokeWidth(4);
        canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(width, 0, width, height, paint);
        canvas.drawLine(width, height, 0, height, paint);
        canvas.drawLine(0, height, 0, 0, paint);
        return highLightBmp;
    }

    @Override
    public void select() {
        selected = true;
    }

    @Override
    public void unSelect() {
        selected = false;
    }

    @Override
    public boolean isSelect() {
        return selected;
    }
}
