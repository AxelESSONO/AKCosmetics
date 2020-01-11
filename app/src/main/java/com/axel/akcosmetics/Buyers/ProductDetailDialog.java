package com.axel.akcosmetics.Buyers;

import android.app.DialogFragment;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.axel.akcosmetics.R;

public class ProductDetailDialog extends DialogFragment
{

    /*START OF RADIOGROUP SIZE VARIABLE*/
    private boolean isCheckingSize = true;
    private int radioChekedSizeId;
    private RadioGroup rGroupSizeRow1;
    private RadioGroup rGroupSizeRow2;
    /*END OF RADIOGROUP SIZE VARIABLE*/

    /*START OF RADIOGROUP COLOR VARIABLE*/
    private boolean isCheckingColor = true;
    private int radioChekedColorId;
    private RadioGroup rGroupColorRow1;
    /*END OF RADIOGROUP COLOR VARIABLE*/

    /*START OF QUANTITY PLUS/MINUS */
    LinearLayout btnDialogQtyPlus;
    LinearLayout btnDialogQtyMinus;
    Button btnDialogQty;
    /*END OF QUANTITY PLUS/MINUS */

    int productQty = 1;

    private Button btnProductDialogOk;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.product_detail_enty_dialog, container, false);


        /*START OF SIZE RADIOGROUP CHECKING*/
        rGroupSizeRow1 = rootView.findViewById(R.id.rgroup_size_row_1);
        rGroupSizeRow2 = rootView.findViewById(R.id.rgroup_size_row_2);

        rGroupSizeRow1.setOnCheckedChangeListener(rGroupCheckedSizeListener1);
        rGroupSizeRow2.setOnCheckedChangeListener(rGroupCheckedSizeListener2);
        /*END OF SIZE RADIOGROUP CHECKING*/


        /*START OF COLOR*/
        rGroupColorRow1 = rootView.findViewById(R.id.rgroup_color_row_1);
        rGroupColorRow1.setOnCheckedChangeListener(rGroupCheckedColorListener1);

        RadioButton radioColor1 = rootView.findViewById(R.id.radio_color_1);
        dialogSetColor(radioColor1, R.color.color_dialog_white);

        RadioButton radioColor2 = rootView.findViewById(R.id.radio_color_2);
        dialogSetColor(radioColor2, R.color.color_dialog_ash);

        RadioButton radioColor3 = rootView.findViewById(R.id.radio_color_3);
        dialogSetColor(radioColor3, R.color.color_dialog_black);

        RadioButton radioColor4 = rootView.findViewById(R.id.radio_color_4);
        dialogSetColor(radioColor4, R.color.color_dialog_blue);

        RadioButton radioColor5 = rootView.findViewById(R.id.radio_color_5);
        dialogSetColor(radioColor5, R.color.color_dialog_pink);

        RadioButton radioColor6 = rootView.findViewById(R.id.radio_color_6);
        dialogSetColor(radioColor6, R.color.color_dialog_yellow);
        /*END OF COLOR*/

        /*START OF OK BUTTON*/
        btnProductDialogOk = rootView.findViewById(R.id.btn_product_detail_dialog_cancel);
        btnProductDialogOk.setOnClickListener(btnProductDialogCencelListener);
        /*END OF OK BUTTON*/

        /*START OF OK BUTTON*/
        btnProductDialogOk = rootView.findViewById(R.id.btn_product_detail_dialog_ok);
        btnProductDialogOk.setOnClickListener(btnProductDialogOkListener);
        /*END OF OK BUTTON*/


        /*START OF QUANTITY PLUS/MINUS*/
        btnDialogQtyPlus = rootView.findViewById(R.id.btn_dialog_cart_product_plus_conatiner);
        btnDialogQtyMinus = rootView.findViewById(R.id.btn_dialog_cart_product_minus_container);
        btnDialogQty = rootView.findViewById(R.id.btn_dialog_cart_product_quantity);

        btnDialogQty.setText(String.valueOf(productQty));

        /*START OF PLUS BTN CLICK LISTENER*/
        btnDialogQtyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productQty++;
                // cartProduct.setQuantity(addedQuantuity);
                btnDialogQty.setText(String.valueOf(productQty));

            }
        });
        /*END OF PLUS BTN CLICK LISTENER*/


        /*START OF MINUS BTN CLICK LISTENER*/
        btnDialogQtyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productQty > 0) {
                    productQty--;
                    //cartProduct.setQuantity(removedQuantuity);
                    btnDialogQty.setText(String.valueOf(productQty));
                }
            }
        });
        /*END OF PLUS BTN CLICK LISTENER*/

        /*END OF QUANTITY PLUS/MINUS*/

        return rootView;

    }

    private void dialogSetColor(RadioButton radioButton, int colorResourceId) {
        radioButton.setBackgroundResource(R.drawable.radio_color_bg);
        GradientDrawable drawable = (GradientDrawable) radioButton.getBackground();
        drawable.setColor(getResources().getColor(colorResourceId));
    }

    /*START OF DIALOG BUTTON CANCEL LISTENER*/
    private Button.OnClickListener btnProductDialogCencelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            dismiss();

        }
    };
    /*END OF DIALOG BUTTON CANCEL LISTENER*/

    /*START OF DIALOG BUTTON OK LISTENER*/
    private Button.OnClickListener btnProductDialogOkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String selectedSize = "";
            String selectedColor = "";

            /*START OF SIZE RADIO CHECKED*/
            switch (radioChekedSizeId) {
                case R.id.radio_size_xs:
                    selectedSize = "xs";
                    break;
                case R.id.radio_size_s:
                    selectedSize = "S";
                    break;
                case R.id.radio_size_m:
                    selectedSize = "M";
                    break;
                case R.id.radio_size_l:
                    selectedSize = "L";
                    break;
                case R.id.radio_size_xl:
                    selectedSize = "XL";
                    break;
                case R.id.radio_size_xxl:
                    selectedSize = "XXL";
                    break;
            }
            /*End OF SIZE RADIO CHECKED*/

            /*START OF COLOR RADIO CHECKED*/
            switch (radioChekedColorId) {
                case R.id.radio_color_1:
                    selectedColor = "COLOR1";
                    break;
                case R.id.radio_color_2:
                    selectedColor = "COLOR2";
                    break;
                case R.id.radio_color_3:
                    selectedColor = "COLOR3";
                    break;
                case R.id.radio_color_4:
                    selectedColor = "COLOR4";
                    break;
                case R.id.radio_color_5:
                    selectedColor = "COLOR5";
                    break;
                case R.id.radio_color_6:
                    selectedColor = "COLOR6";
                    break;
            }
            Toast.makeText(getActivity(), "QTY : " + productQty + " Size : " + selectedSize + "; Color : " + selectedColor, Toast.LENGTH_SHORT).show();
            /*End OF COLOR RADIO CHECKED*/

            dismiss();
        }
    };
    /*END OF DIALOG BUTTON OK LISTENER*/


    /*START OF RADIO GROUP COLOR LISTENER*/
    private RadioGroup.OnCheckedChangeListener rGroupCheckedColorListener1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1 && isCheckingColor) {
                isCheckingColor = false;
                //rGroupSizeRow2.clearCheck();
                radioChekedColorId = checkedId;
            }
            isCheckingColor = true;
        }
    };
    /*END OF RADIO GROUP COLOR LISTENER*/


    /*START OF RADIO GROUP SIZE LISTENET*/
    private RadioGroup.OnCheckedChangeListener rGroupCheckedSizeListener1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1 && isCheckingSize) {
                isCheckingSize = false;
                rGroupSizeRow2.clearCheck();
                radioChekedSizeId = checkedId;
            }
            isCheckingSize = true;
        }
    };
    private RadioGroup.OnCheckedChangeListener rGroupCheckedSizeListener2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1 && isCheckingSize) {
                isCheckingSize = false;
                rGroupSizeRow1.clearCheck();
                radioChekedSizeId = checkedId;
            }
            isCheckingSize = true;
        }
    };
    /*END OF RADIO GROUP SIZE LISTENET*/


}
