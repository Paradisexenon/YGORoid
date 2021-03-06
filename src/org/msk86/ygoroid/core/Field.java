package org.msk86.ygoroid.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import org.msk86.ygoroid.utils.Configuration;
import org.msk86.ygoroid.utils.Utils;

public class Field implements Item, Drawable {
    private FieldType type;
    private SelectableItem setItem;

    public Field(FieldType type) {
        this.type = type;
    }

    public FieldType getType() {
        return type;
    }

    public void setItem(SelectableItem item) {
        this.setItem = item;
    }

    public SelectableItem getItem() {
        return setItem;
    }

    public SelectableItem removeItem() {
        SelectableItem item = this.setItem;
        this.setItem = null;
        return item;
    }

    public Card getTopCard() {
        if (setItem != null) {
            if (setItem instanceof Card) {
                return (Card) setItem;
            } else if (setItem instanceof OverRay) {
                return ((OverRay) setItem).topCard();
            } else if (setItem instanceof CardList) {
                return ((CardList) setItem).topCard();
            }
        }
        return null;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        drawFrame(canvas, x, y);
        if (setItem != null) {
            Drawable drawable = (Drawable) setItem;
            helper.drawDrawable(canvas, drawable, helper.center(width(), drawable.width()), padding());
        }
    }

    private void drawFrame(Canvas canvas, int x, int y) {
        Utils.DrawHelper helper = new Utils.DrawHelper(x, y);
        Paint paint = new Paint();
        paint.setColor(Configuration.fieldColor());
        paint.setAlpha(80);
        helper.drawRect(canvas, new Rect(padding(), padding(), width() - padding(), height() - padding()),
                paint);

        paint.setColor(Configuration.lineColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(255);

        helper.drawRect(canvas, new Rect(padding(), padding(), width() - padding(), height() - padding()),
                paint);
    }

    @Override
    public int width() {
        return Utils.unitLength();
    }

    @Override
    public int height() {
        return Utils.unitLength();
    }

    public int padding() {
        return 2;
    }
}
