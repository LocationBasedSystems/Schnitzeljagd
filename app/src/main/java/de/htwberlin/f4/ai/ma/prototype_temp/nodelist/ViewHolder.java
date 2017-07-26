package de.htwberlin.f4.ai.ma.prototype_temp.nodelist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carol.bvg.R;

/**
 * Created by Johann Winter
 */

class ViewHolder {
    ImageView nodeImageView;
    TextView nodeIdTextView;
    TextView nodeDescriptionTextView;

    ViewHolder(View view) {
        nodeImageView = (ImageView) view.findViewById(R.id.preview_imageview);
        nodeIdTextView = (TextView) view.findViewById(R.id.node_name);
        nodeDescriptionTextView = (TextView) view.findViewById(R.id.node_description);
    }
}