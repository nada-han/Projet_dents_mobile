package ma.khabbachi.dentaire;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private FloatingActionButton button;
    private Bitmap originalBitmap;
    private Paint paint;
    private Canvas canvas;
    private Mat edges;


    private List<Point> selectedPoints = new ArrayList<>();


    private List<Point> dentBorderPointsList = new ArrayList<>();


    private List<Point> storPoints = new ArrayList<>();
    private int comp = 0;
    private double taperAngleDegLeft = 0;
    private double taperAngleDegRight = 0;

    private double convergenceAngle = 0;


    private double angleLeftLine = 0;
    private double angleRightLine = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (OpenCVLoader.initDebug()) {
            Toast.makeText(this, "OpenCV Loaded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "OpenCV not loaded", Toast.LENGTH_SHORT).show();
        }

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.floatingActionButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(MainActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
        Button btnShowPopup = findViewById(R.id.btnShowPopup);
        btnShowPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Appeler la méthode showPopupDialog pour afficher les résultats
                // Appelez cette méthode après avoir calculé les angles
                showTaperAnglesDialog(taperAngleDegLeft, taperAngleDegRight);

            }
        });

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFFFF0000);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(20); // Épaisseur du trait
    }
    private void showTaperAnglesDialog(double taperAngleDeg, double taperAngleDeg2) {
        // Utilisez AlertDialog ou tout autre composant de popup que vous préférez
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Résultats");

        // Formatez les angles pour afficher trois chiffres après la virgule
        String formattedMessage = String.format("Taper Angle 1: %.3f\nTaper Angle 2: %.3f\nAngle de convergence: %.3f", taperAngleDegLeft, taperAngleDegRight, convergenceAngle);

        builder.setMessage(formattedMessage);

        // Ajoutez un bouton "OK" pour fermer le popup
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Afficher le popup
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            // Charger l'image en utilisant OpenCV
            Mat originalImage = Imgcodecs.imread(uri.getPath());

            // Convertir l'image en niveaux de gris
            Mat grayImage = new Mat();
            Imgproc.cvtColor(originalImage, grayImage, Imgproc.COLOR_BGR2GRAY);

            dentBorderPointsList = new ArrayList<>();
            drawOptimalPoints(dentBorderPointsList);


            // Appliquer un flou gaussien
            Imgproc.GaussianBlur(grayImage, grayImage, new Size(5, 5), 0);

            // Detect edges using the Canny algorithm
            edges = new Mat();
            Imgproc.Canny(grayImage, edges, 50, 150);

            // Appliquer dilation pour connecter les composants
            Imgproc.dilate(edges, edges, new Mat(), new Point(-1, -1), 2);

            // Afficher l'image traitée
            originalBitmap = matToBitmap(edges);
            imageView.setImageBitmap(originalBitmap);



            // Vérifier que dentBorderPointsList est initialisé et non nul
            if (dentBorderPointsList != null) {
                // Après la détection des bords (avant la création du Canvas)
                dentBorderPointsList = findContourPoints(edges);
                // Configurer le Canvas pour dessiner
                canvas = new Canvas(originalBitmap);
                imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        float x = event.getX();
                        float y = event.getY();

                        // Vérifier si l'action de l'événement est ACTION_DOWN
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            // Convertir les coordonnées de l'événement touch en coordonnées d'image
                            Matrix inverse = new Matrix();
                            imageView.getImageMatrix().invert(inverse);
                            float[] point = new float[]{x, y};
                            inverse.mapPoints(point);

                            x = point[0];
                            y = point[1];

                            // Ajouter le point à la liste des points sélectionnés
                            Point selectedPoint = new Point(x, y);

                            // Vérifier que dentBorderPointsList est initialisé et non nul
                            if (dentBorderPointsList != null) {
                                // Trouver le point le plus proche et optimal parmi les points sur les bords
                                Point closestOptimalPoint = findClosestOptimalPoint(selectedPoint, dentBorderPointsList);

                                // Dans votre méthode onTouch
                                if (closestOptimalPoint != null) {
                                    // Vérifier si le point optimal est à l'intérieur des bords et n'a pas été déjà sélectionné
                                    if (isPointInsideContour(closestOptimalPoint, dentBorderPointsList) && !selectedPoints.contains(closestOptimalPoint)) {
                                        // Dessiner un cercle à l'emplacement du point sélectionné
                                        //canvas.drawCircle((float) selectedPoint.x, (float) selectedPoint.y, 10, paint);

                                        // Dessiner un cercle à l'emplacement du point optimal
                                        canvas.drawCircle((float) closestOptimalPoint.x, (float) closestOptimalPoint.y, 10, paint);

                                        // Mettre à jour l'image affichée
                                        imageView.invalidate();

                                        // Afficher les coordonnées dans la console Log
                                        Log.d("MyTag", "Selected Point X: " + selectedPoint.x + ", Y: " + selectedPoint.y);
                                        Log.d("MyTag", "Optimal Point X: " + closestOptimalPoint.x + ", Y: " + closestOptimalPoint.y);

                                        // Ajouter le point le plus proche et optimal à la liste des points sélectionnés
                                        selectedPoints.add(closestOptimalPoint);

                                        // Si vous avez suffisamment de points, vous pouvez appeler la détection de lignes ici
                                        if (selectedPoints.size() == 4) {
                                            performHoughLinesDetection();
                                        }
                                    }
                                }


                            }
                        }
                        return true;
                    }
                });
            }
        }
    }


    private Bitmap matToBitmap(Mat mat) {
        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }

    // Ajouter cette méthode à votre classe MainActivity
    private boolean isPointInsideContour(Point point, List<Point> contourPoints) {
        // Créer une matrice de points de contour
        MatOfPoint2f contourMat = new MatOfPoint2f(contourPoints.toArray(new Point[0]));

        // Vérifier si le point est à l'intérieur des bords en utilisant pointPolygonTest
        double distance = Imgproc.pointPolygonTest(contourMat, point, true);

        // Si la distance est positive, le point est à l'intérieur des bords
        return distance >= 0;
    }

    private Point findClosestOptimalPoint(Point touchPoint, List<Point> optimalPoints) {
        Point closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Point optimalPoint : optimalPoints) {
            double distance = calculateDistance(touchPoint, optimalPoint);

            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = optimalPoint;
            }
        }

        return closestPoint;
    }

    // Ajouter cette méthode à votre classe MainActivity
    private List<Point> findContourPoints(Mat edges) {
        List<Point> contourPoints = new ArrayList<>();
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();

        // Trouver les contours dans l'image d'arêtes
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Ajouter les points de contour à la liste
        for (MatOfPoint contour : contours) {
            contourPoints.addAll(contour.toList());
        }

        return contourPoints;
    }


    private double calculateDistance(Point p1, Point p2) {
        if (p1 != null && p2 != null) {
            double deltaX = p1.x - p2.x;
            double deltaY = p1.y - p2.y;
            return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        }
        return Double.MAX_VALUE; // Ou une autre valeur appropriée si l'un des points est nul
    }




    // Dans votre méthode drawOptimalPoints, dessinez les points optimaux
    private void drawOptimalPoints(List<Point> points) {
        if (canvas != null && points != null) {
            for (Point point : points) {
                canvas.drawCircle((float) point.x, (float) point.y, 10, paint);
            }
            imageView.invalidate();
        }
    }


    private void performHoughLinesDetection() {
        try {
            if (originalBitmap != null && selectedPoints.size() == 4) {
                // Convertir la Bitmap en Mat
                Mat originalMat = new Mat();
                Utils.bitmapToMat(originalBitmap, originalMat);

                // Convertir les points en MatOfPoint2f pour la détection de lignes
                MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
                matOfPoint2f.fromList(selectedPoints);

                // Appliquer la détection des lignes de Hough avec la matrice edges
                Mat lines = new Mat();
                Imgproc.HoughLines(edges, lines, 1, Math.PI / 180, 150);

                // Initialiser les variables pour les lignes gauche et droite les plus optimales
                Line bestLeftLine = null;
                Line bestRightLine = null;
                double imageCenterX = originalMat.cols() / 2.0;

                // Filtrer les lignes horizontales (lignes non verticales)
                for (int x = 0; x < lines.rows(); x++) {
                    double[] vec = lines.get(x, 0);
                    double rho = vec[0];
                    double theta = vec[1];
                    double a = Math.cos(theta);
                    double b = Math.sin(theta);
                    double x0 = a * rho;
                    double y0 = b * rho;
                    Point pt1 = new Point(Math.round(x0 + 1000 * (-b)), Math.round(y0 + 1000 * (a)));
                    Point pt2 = new Point(Math.round(x0 - 1000 * (-b)), Math.round(y0 - 1000 * (a)));

                    // Déterminer si la ligne est à gauche ou à droite en fonction du point de départ
                    boolean isLeftLine = pt1.x < imageCenterX;
                    boolean isRightLine = pt1.x >= imageCenterX;

                    // Ligne gauche
                    if (isLeftLine) {
                        if (bestLeftLine == null || pt1.x < bestLeftLine.getStartPoint().x) {
                            bestLeftLine = new Line(vec);
                        }
                    }
                    // Ligne droite
                    else if (isRightLine) {
                        if (bestRightLine == null || pt1.x > bestRightLine.getStartPoint().x) {
                            bestRightLine = new Line(vec);
                        }
                    }
                }

                // Convertir le résultat de Mat à Bitmap si nécessaire
                Bitmap resultBitmap = matToBitmap(originalMat);

                // Afficher l'image résultante dans une ImageView ou faire d'autres traitements
                imageView.setImageBitmap(resultBitmap);

                // Initialiser le Canvas après l'affichage de l'image résultante
                canvas = new Canvas(resultBitmap);

                // Dessiner les points optimaux sur les bords
                //drawOptimalPoints(dentBorderPointsList);

                // À ajouter après le dessin des lignes gauche et droite
                if (bestLeftLine != null && bestRightLine != null) {
                    // Calculer l'angle des lignes gauche et droite
                    double angleLeftLine = Math.toDegrees(Math.atan(bestLeftLine.getSlope()));
                    double angleRightLine = Math.toDegrees(Math.atan2(bestRightLine.getEndY() - bestRightLine.getStartY(),
                            bestRightLine.getEndX() - bestRightLine.getStartX()));

                    // Calculer les coordonnées des points sur les lignes gauche et droite
                    Point leftPoint1 = selectedPoints.get(0);
                    Point leftPoint2 = selectedPoints.get(1);
                    Point rightPoint1 = selectedPoints.get(2);
                    Point rightPoint2 = selectedPoints.get(3);

                    // Dessiner les tangentes passant par les points sélectionnés
                    drawTangents(Arrays.asList(selectedPoints.get(0), selectedPoints.get(1), selectedPoints.get(2), selectedPoints.get(3)));

                    calculateAndDisplayTaperAngles(Arrays.asList(leftPoint1, leftPoint2, rightPoint1, rightPoint2), comp);

                    // Afficher les angles dans un contexte graphique
                    paint.setColor(Color.YELLOW);
                    paint.setTextSize(20);

                    float centerX = (float) ((selectedPoints.get(0).x + selectedPoints.get(1).x) / 2);
                    float centerY = (float) ((selectedPoints.get(0).y + selectedPoints.get(1).y) / 2);
                    canvas.drawText(String.format(" %.2f °", taperAngleDegLeft), centerX - 40, centerY - 20, paint);

                    float centerX2 = (float) ((selectedPoints.get(2).x + selectedPoints.get(3).x) / 2);
                    float centerY2 = (float) ((selectedPoints.get(2).y + selectedPoints.get(3).y) / 2);
                    canvas.drawText(String.format("%.2f °", taperAngleDegRight), centerX2 - 40, centerY2 - 20, paint);

                }

            } else {
                // Log ou gestion d'erreur en cas de Bitmap nulle ou de nombre de points incorrect
                Log.d("MyTag", "performHoughLinesDetection: originalBitmap is null or invalid number of points");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateAndDisplayTaperAngles(List<Point> points, int comp) {
        // Calculer les distances pour l'angle de cône du côté gauche
        double deltaYLeft = points.get(1).y - points.get(0).y;
        double deltaXLeft = points.get(1).x - points.get(0).x;
        double taperAngleRadLeft = Math.atan2(deltaYLeft, deltaXLeft);
        double taperAngleDegLeft1 = 0;

        if (points.isEmpty()) {
            taperAngleDegLeft1 = Math.toDegrees(taperAngleRadLeft);
        } else if (comp == 0) {
            taperAngleDegLeft1 = 90 + Math.toDegrees(taperAngleRadLeft);
        } else if (comp == 1) {
            taperAngleDegLeft1 = 90 + Math.toDegrees(taperAngleRadLeft);
        }

        // Calculer les distances pour l'angle de cône du côté droit
        double deltaYRight = points.get(3).y - points.get(2).y;
        double deltaXRight = points.get(3).x - points.get(2).x;
        double taperAngleRadRight = Math.atan2(deltaYRight, deltaXRight);

        double taperAngleDegRight1 = 0;
        if (points.isEmpty()) {
            taperAngleDegRight1 = -Math.toDegrees(taperAngleRadRight);
        } else if (comp == 0) {
            taperAngleDegRight1 = -Math.toDegrees(taperAngleRadRight) + 90;
        } else if (comp == 1) {
            taperAngleDegRight1 = -Math.toDegrees(taperAngleRadRight) + 90;
        }

        double convergenceAngle1 = taperAngleDegLeft1 + taperAngleDegRight1;

        // Afficher les résultats dans les logs

        Log.d("TaperAngles", "Taper Angle Left: " + taperAngleDegLeft1 + "\nTaper Angle Right: " + taperAngleDegRight1 +
                "\nConvergence Angle: " + convergenceAngle1);
        this.taperAngleDegLeft = taperAngleDegLeft1;
        this.taperAngleDegRight = taperAngleDegRight1;
        this.convergenceAngle = convergenceAngle1;
        Angle s = new Angle(0L,taperAngleDegLeft1, taperAngleDegRight1,convergenceAngle1);
        addAngle(s);

    }



    // Ajouter cette méthode à votre classe MainActivity
    private double calculateAngleBetweenLines(Line line1, Line line2) {
        double slope1 = line1.getSlope();
        double slope2 = line2.getSlope();

        // Calculer l'angle entre les deux tangentes en radians
        double angleRad = Math.atan(Math.abs((slope2 - slope1) / (1 + slope1 * slope2)));

        // Convertir l'angle en degrés
        double angleDeg = Math.toDegrees(angleRad);

        return angleDeg;
    }

    private void drawTangents(List<Point> points) {
        if (canvas != null && points != null && points.size() == 4) {
            paint.setColor(0xFF00FF00); // Changer la couleur pour les tangentes
            paint.setStrokeWidth(4); // Changer l'épaisseur du trait pour les tangentes

            // Extraire les points des bords de la dent
            Point leftPoint1 = points.get(0);
            Point leftPoint2 = points.get(1);
            Point rightPoint1 = points.get(2);
            Point rightPoint2 = points.get(3);

            // Calculer les intersections des tangentes
            Point intersection = calculateIntersection(leftPoint1, leftPoint2, rightPoint1, rightPoint2);

            // Dessiner les tangentes passant par les points sélectionnés
            if (intersection != null) {
                drawTangent(leftPoint1, intersection);
                drawTangent(leftPoint2, intersection);
                drawTangent(rightPoint1, intersection);
                drawTangent(rightPoint2, intersection);
            }

            imageView.invalidate();
        }
    }

    // Méthode pour dessiner une tangente reliant un point à l'intersection
    private void drawTangent(Point point, Point intersection) {
        // Calculer la pente de la tangente
        double tangentSlope = (intersection.y - point.y) / (intersection.x - point.x);

        // Calculer les coordonnées des extrémités de la tangente
        double length = 150; // Changer la longueur de la tangente si nécessaire
        double xEnd = point.x + length;
        double yEnd = point.y + length * tangentSlope;
        double xStart = point.x - length;
        double yStart = point.y - length * tangentSlope;

        // Dessiner la tangente sur le Canvas
        canvas.drawLine((float) xStart, (float) yStart, (float) xEnd, (float) yEnd, paint);
    }


    private Point calculateIntersection(Point point1, Point point2, Point point3, Point point4) {
        // Calculer les coefficients des équations des droites
        double x1 = point1.x, y1 = point1.y;
        double x2 = point2.x, y2 = point2.y;
        double x3 = point3.x, y3 = point3.y;
        double x4 = point4.x, y4 = point4.y;

        double det = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        // Vérifier si les droites sont parallèles (déterminant égal à zéro)
        if (Math.abs(det) == 0) {
            return null; // Les droites sont parallèles, pas d'intersection
        }

        // Calculer les coordonnées du point d'intersection
        double intersectX = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / det;
        double intersectY = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / det;

        return new Point(intersectX, intersectY);
    }




    // Ajouter une classe Line pour stocker les informations sur la ligne
    private static class Line {
        private final double rho;
        private final double theta;

        public Line(double[] vec) {
            this.rho = vec[0];
            this.theta = vec[1];
        }

        public Point getStartPoint() {
            double a = Math.cos(theta);
            double b = Math.sin(theta);
            double x0 = a * rho;
            double y0 = b * rho;
            return new Point(Math.round(x0 + 1000 * (-b)), Math.round(y0 + 1000 * (a)));
        }

        public Point getEndPoint() {
            double a = Math.cos(theta);
            double b = Math.sin(theta);
            double x0 = a * rho;
            double y0 = b * rho;
            return new Point(Math.round(x0 - 1000 * (-b)), Math.round(y0 - 1000 * (a)));
        }

        public double getSlope() {
            return -Math.tan(theta); // N'oubliez pas le signe négatif pour obtenir la pente correcte
        }

        public double getStartX() {
            return getStartPoint().x;
        }

        public double getStartY() {
            return getStartPoint().y;
        }

        public double getEndX() {
            return getEndPoint().x;
        }

        public double getEndY() {
            return getEndPoint().y;
        }
    }
    public void addAngle(Angle angle) {
        AngleApi angleApi = RetrofitAngle.getClient().create(AngleApi.class);
        Call<Void> call = angleApi.addAngle(angle);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("rep", "Success: " + response.toString());
                } else {
                    Log.d("rep", "Error: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("rep", "Failure: " + t.getMessage(), t);
            }
        });

    }
}