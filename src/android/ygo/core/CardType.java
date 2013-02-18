package android.ygo.core;

import android.graphics.Bitmap;
import android.ygo.R;
import android.ygo.utils.Utils;

public enum CardType {
    NULL(0, "卡片不存在，您可能需要更新数据库文件！", R.raw.card_unknown),
    MONSTER(Const.TYPE_MONSTER, "怪兽", 0),
    SPELL(Const.TYPE_SPELL, "魔法", R.raw.card_magic),
    TRAP(Const.TYPE_TRAP, "陷阱", R.raw.card_trap);

    private int code;
    private String text;
    private int resId;
    private Bitmap cardBmp;

    CardType(int code, String text, int resId) {
        this.code = code;
        this.text = text;
        this.resId = resId;
        if(this.resId != 0) {
            cardBmp = Utils.readBitmapScaleByHeight(resId, Utils.cardHeight());
        }
    }

    public static CardType getCardType(int code) {
        for (CardType type : CardType.values()) {
            if (type == NULL) {
                continue;
            }
            if ((code & type.code) == type.code) {
                return type;
            }
        }
        return NULL;
    }

    public Bitmap getCardBmp() {
        return cardBmp;
    }

    @Override
    public String toString() {
        return text;
    }
}
