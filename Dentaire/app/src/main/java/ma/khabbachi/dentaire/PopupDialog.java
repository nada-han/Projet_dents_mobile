package ma.khabbachi.dentaire;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class PopupDialog {
    public static void showImageDialog(Context context, Bitmap bitmap, double angleLeftLine, double angleRightLine, double convergenceAngle) {
        // Créer une boîte de dialogue personnalisée
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Définir le layout personnalisé pour la boîte de dialogue
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.results_popup, null);
        dialog.setContentView(dialogView);

        // Initialiser les TextView dans le layout personnalisé
        TextView textViewLeftAngle = dialog.findViewById(R.id.textViewLeftAngle);
        TextView textViewRightAngle = dialog.findViewById(R.id.textViewRightAngle);
        TextView textViewConvergenceAngle = dialog.findViewById(R.id.textViewConvergenceAngle);

        // Afficher les angles dans les TextView
        textViewLeftAngle.setText("Angle Left Line: " + angleLeftLine);
        textViewRightAngle.setText("Angle Right Line: " + angleRightLine);
        textViewConvergenceAngle.setText("Convergence Angle: " + convergenceAngle);

        // Afficher la boîte de dialogue
        dialog.show();
    }
}

