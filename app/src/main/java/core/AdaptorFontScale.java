package core;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigfont.demo.R;

import java.util.ArrayList;


/**
 * Created by d on 9/6/2017.
 */

public class AdaptorFontScale extends ArrayAdapter<ItemFont> {
    private Context context;
    private ArrayList<ItemFont> objects;
    MainActivity mainActivity;
    //    private AdView adViewPlayer;

    public AdaptorFontScale(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ItemFont> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
        mainActivity = (MainActivity) context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        float fontScaleSave = MyLog.getFloatValueByName(mainActivity, Config.LOG_APP, Config.FONT_SCALE);
        View view = View.inflate(context, R.layout.item_font, null);
        TextView tvTitle = view.findViewById(R.id.item_font_title),
                tvViewText1 = view.findViewById(R.id.item_font_view_text_1),
                tvViewText2 = view.findViewById(R.id.item_font_view_text_2);
        Button btnState = view.findViewById(R.id.item_font_btn_state);
        ImageView imSelected = view.findViewById(R.id.item_font_im_selected);
        tvTitle.setText(objects.get(position).getTitle());
        tvViewText1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 * objects.get(position).getSize());
        tvViewText2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12 * objects.get(position).getSize());
        for (ItemFont itemFont : Config.getItemFonts(mainActivity))
            itemFont.setSelect(false);
        if (fontScaleSave == objects.get(position).getSize())
            objects.get(position).setSelect(true);
        try {
            if (objects.get(position).isSelect()) {
                try {
                    btnState.setBackground(context.getResources().getDrawable(R.drawable.shape_btn_item_font_select));
                } catch (Exception e) {
                }
                btnState.setText(context.getResources().getString(R.string.txt_btn_selected));
                imSelected.setVisibility(View.VISIBLE);
            } else {
                try {
                    btnState.setBackground(context.getResources().getDrawable(R.drawable.shape_btn_item_font_unselect));
                } catch (Exception e) {
                }
                btnState.setText(context.getResources().getString(R.string.txt_btn_unselected));
                imSelected.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String logevent = "MainScreen_ButtonAplly_" + objects.get(position).getTitle() + "_Clicked";
                logevent = logevent.replace(" ", "_");
                mainActivity.logger.logEvent(logevent);
                showDiaLog(position);

            }
        });
        return view;
    }


    AlertDialog.Builder builder;

    private void showDiaLog(final int position) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder = new AlertDialog.Builder(context);
        final View view = View.inflate(context, R.layout.dialog_choose, null);
        Button btnYes = view.findViewById(R.id.btn_yes),
                btnCancel = view.findViewById(R.id.btn_cancel);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mainActivity.requestPermissionAndApplyFontSize(Config.getItemFonts(mainActivity).get(position));
                mainActivity.logger.logEvent("DialogAskSetSize_ButtonYes_Clicked");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mainActivity.logger.logEvent("DialogAskSetSize_ButtonCancel_Clicked");
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
}
