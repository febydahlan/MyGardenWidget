package com.example.android.mygarden;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.mygarden.ui.MainActivity;

public class PlantWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget (Context context, AppWidgetManager appWidgetManager, int imgRes, int id){
        Intent intent = new Intent(context, MainActivity.class);

        //karna intent di jenis classs yg berbeda, jadi buatkan pending Intent

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.plant_widget);
        remoteViews.setImageViewResource(R.id.widget_plant_image, imgRes);
        remoteViews.setOnClickPendingIntent(R.id.widget_plant_image, pendingIntent);

        Intent intentService = new Intent(context, PlantWateringService.class);
        intentService.setAction(PlantWateringService.ACTION_WATER_PLANTS);
        PendingIntent pendingIntentService = PendingIntent.getService(context,
                0,
                intentService,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_water_button, pendingIntentService);

        appWidgetManager.updateAppWidget(id, remoteViews);

    }

    public static void updatePlantWidget(Context context, AppWidgetManager manager, int imgRes, int[] ids) {
        for (int id : ids){
            updateAppWidget(context, manager,imgRes, id);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
       PlantWateringService.startActionUpdatePlants(context);
    }
}
