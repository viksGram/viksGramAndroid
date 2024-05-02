package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.ui.ActionBar.Theme;

public class ColoredImageSpan extends ReplacementSpan {

    int drawableColor;
    public Drawable drawable;

    boolean usePaintColor = true;
    int colorKey;
    private int topOffset = 0;
<<<<<<< HEAD
=======
    private float translateX, translateY, rotate;
    private float alpha = 1f;
    private int overrideColor;
>>>>>>> d494ea8cb (update to 10.12.0 (4710))

    private int size;

    public static final int ALIGN_DEFAULT = 0;
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_CENTER = 2;
    private final int verticalAlignment;

    public ColoredImageSpan(int imageRes) {
        this(imageRes, ALIGN_DEFAULT);
    }

    public ColoredImageSpan(Drawable drawable) {
        this(drawable, ALIGN_DEFAULT);
    }

    public ColoredImageSpan(int imageRes, int verticalAlignment) {
        this(ContextCompat.getDrawable(ApplicationLoader.applicationContext, imageRes), verticalAlignment);
    }

    public ColoredImageSpan(Drawable drawable, int verticalAlignment) {
        this.drawable = drawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        this.verticalAlignment = verticalAlignment;
    }

    public void setSize(int size) {
        this.size = size;
        drawable.setBounds(0, 0, size, size);
    }

<<<<<<< HEAD
    @Override
    public int getSize(@NonNull Paint paint, CharSequence charSequence, int i, int i1, @Nullable Paint.FontMetricsInt fontMetricsInt) {
        return size != 0 ? size : drawable.getIntrinsicWidth();
=======
    public void setTranslateX(float tx) {
        translateX = tx;
    }

    public void setTranslateY(float ty) {
        translateY = ty;
    }

    public void translate(float tx, float ty) {
        translateX = tx;
        translateY = ty;
    }

    public void rotate(float r) {
        rotate = r;
    }

    public void setWidth(int width) {
        sizeWidth = width;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence charSequence, int i, int i1, @Nullable Paint.FontMetricsInt fm) {
        if (isRelativeSize && fontMetrics != null) {
            if (fm == null) {
                fm = new Paint.FontMetricsInt();
            }
            if (fm != null) {
                fm.ascent = fontMetrics.ascent;
                fm.descent = fontMetrics.descent;

                fm.top = fontMetrics.top;
                fm.bottom = fontMetrics.bottom;
            }
            return (int) (Math.abs(scaleX) * Math.abs(spaceScaleX) * size);
        }
        if (sizeWidth != 0)
            return (int) (Math.abs(scaleX) * sizeWidth);
        return (int) (Math.abs(scaleX) * Math.abs(spaceScaleX) * (size != 0 ? size : drawable.getIntrinsicWidth()));
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        int color;
        if (usePaintColor) {
            color = paint.getColor();
        } else {
            color = Theme.getColor(colorKey);
        }
        if (drawableColor != color) {
            drawableColor = color;
            drawable.setColorFilter(new PorterDuffColorFilter(drawableColor, PorterDuff.Mode.MULTIPLY));
        }

        canvas.save();
        int transY = bottom - (drawable != null ? drawable.getBounds().bottom : bottom);
        if (verticalAlignment == ALIGN_BASELINE) {
            transY -= paint.getFontMetricsInt().descent;
        } else if (verticalAlignment == ALIGN_CENTER) {
            transY = top + (bottom - top) / 2 - (drawable != null ? drawable.getBounds().height() / 2 : 0);
        } else if (verticalAlignment == ALIGN_DEFAULT) {
            int lineHeight = bottom - top;
            int drawableHeight = size != 0 ? size : drawable.getIntrinsicHeight();
            int padding = (lineHeight - drawableHeight) / 2;
            transY = top + padding + AndroidUtilities.dp(topOffset);
        }
        canvas.translate(x, transY);
        if (drawable != null) {
<<<<<<< HEAD
=======
            if (scaleX != 1f || scaleY != 1f) {
                canvas.scale(scaleX, scaleY, 0, drawable.getBounds().centerY());
            }
            if (rotate != 1f) {
                canvas.rotate(rotate, drawable.getBounds().centerX(), drawable.getBounds().centerY());
            }
            if (alpha != 1f || paint.getAlpha() != 0xFF) {
                drawable.setAlpha((int) (alpha * paint.getAlpha()));
            }
>>>>>>> d494ea8cb (update to 10.12.0 (4710))
            drawable.draw(canvas);
        }
        canvas.restore();
    }

    public void setColorKey(int colorKey) {
        this.colorKey = colorKey;
        usePaintColor = colorKey < 0;
    }

    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }
}
