package adneom.poc_clustering_map.ClusterRender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import adneom.poc_clustering_map.R;
import adneom.poc_clustering_map.model.MyItem;

/**
 * Created by gtshilombowanticale on 10-07-17.
 */

public class TotalRender extends DefaultClusterRenderer<MyItem> {

    private Context context;

    private IconGenerator mClusterIconGenerator;

    public TotalRender(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
        mClusterIconGenerator = new IconGenerator(context);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster cluster, MarkerOptions markerOptions) {
        //my special marker
        IconGenerator TextMarkerTotal = new IconGenerator(context);
        Drawable marker;

        //my image
        marker = context.getResources().getDrawable(R.drawable.summary);
        TextMarkerTotal.setBackground(marker);

        //layout
        LayoutInflater myInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = myInflater.inflate(R.layout.layout_cluster_view, null, false);

        ((TextView) activityView.findViewById(R.id.text)).setText(String.valueOf(cluster.getSize()));
        TextMarkerTotal.setContentView(activityView);
        //getting number
        TextMarkerTotal.makeIcon(String.valueOf(cluster.getSize()));

        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(TextMarkerTotal.makeIcon());
        markerOptions.icon(icon);
    }

    @Override
    protected void onClusterItemRendered(MyItem clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        //super.onBeforeClusterItemRendered(item, markerOptions);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.normal));
    }
}
