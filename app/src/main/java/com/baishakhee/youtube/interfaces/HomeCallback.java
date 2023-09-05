package com.baishakhee.youtube.interfaces;

import android.view.View;

import com.baishakhee.youtube.model.Items;


public interface HomeCallback{

    void onClickItemsCallback(View view, int position, Items videoItem);

}
