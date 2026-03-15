package io.github.h_aliueia;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

public class tutorial extends AppIntro
{

    public int getStatusBarHeight()
    {
        int result = 0;
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        if (resourceId > 0)
        {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getNavigationBarHeight()
    {
        int result = 0;
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setPadding(0, getStatusBarHeight(), 0, getNavigationBarHeight());
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (v, insets) -> {
            return ViewCompat.onApplyWindowInsets(v, WindowInsetsCompat.CONSUMED);
        });
        EdgeToEdge.enable(this);
        addSlide(AppIntroFragment.createInstance(
            "Πως λειτουργεί η εφαρμογή",
            "Άμα δεν ενδιαφέρεστε να μάθετε πατήστε παράλειψη",
            R.mipmap.ic_launcher,
            R.color.part1
        ));
        addSlide(AppIntroFragment.createInstance(
            "Γραμμή Μενού",
            "Υπάρχουν 4 διαφορετικά εργαλεία στην εφαρμογή\n" +
                "\n" +
                "1) Άρθρα\n" +
                "2) Αποσπάσματα\n" +
                "3) Shorts (Μικρά Βίντεο)\n" +
                "4) Μαθήματα\n" +
                "\n" +
                "Πατώντας σε ένα από αυτά σε βγάζει σε διαφορετικό μενού για διαφορετικές λειτουργίες",
            R.drawable.tut1,
            R.color.part1
        ));
        addSlide(AppIntroFragment.createInstance(
            "Αποσπάσματα",
            "Στο Μενού αποσπάσματα\n" +
                "Μπορείτε να πατήσετε το μεσαίο κουμπί στην γραμμή μενού\n" +
                "Για να αλλάξετε το απόσπασμα που φαίνεται στην οθόνη",
            R.drawable.tut2,
            R.color.part1
        ));
        addSlide(AppIntroFragment.createInstance(
            "Αποσπάσματα",
            "Στο Μενού αποσπάσματα\n" +
                "Μπορείτε να πατήσετε το κουμπί πηγή πάνω δεξιά\n" +
                "Για να δείτε την πηγή αυτού του αποσπάσματος\n" +
                "Άμα ξαναπατήσετε αυτό το κουμπί θα πάτε πίσω στο απόσπασμα",
            R.drawable.tut3,
            R.color.part1
        ));
        addSlide(AppIntroFragment.createInstance(
            "Αποσπάσματα",
            "Στο Μενού αποσπάσματα\n" +
                "Μπορείτε να πατήσετε το κουμπί αποθήκευσης πάνω αριστερά\n" +
                "Για να αποθηκεύσετε ένα απόσπασμα\n" +
                "\n" +
                "Απλό πάτημα = αποθήκευση(ή αφαίρεση άμα είναι αποθηκευμένο)\n" +
                "(όταν το κουμπί πατιέται εμφανίζετε μία ένδειξη από κάτω άμα αποθηκεύτηκε ή διαγράφτηκε)\n" +
                "Παρατεταμένο πάτημα = εμφάνιση της λίστας αποθηκευμένων αποσπασμάτων",
            R.drawable.tut4,
            R.color.part1
        ));
        addSlide(AppIntroFragment.createInstance(
            "Shorts (Μικρά Βίντεο)",
            "Στο Μενού των Shorts (Μικρά Βίντεο)\n" +
                "Μπορείτε να σύρετε προς τα πάνω για να δείτε το επόμενο βίντεο",
            R.drawable.tut5,
            R.color.part1
        ));
        addSlide(AppIntroFragment.createInstance(
            "Shorts (Μικρά Βίντεο)",
            "Στο Μενού των Shorts (Μικρά Βίντεο)\n" +
                "Μπορείτε να πατήσετε το μεσαίο κουμπί στην γραμμή μενού\n" +
                "Για να δείτε και να διαλέξετε όλες τις κατηγορίες για τα μικρά βίντεο",
            R.drawable.tut6,
            R.color.part1
        ));
        addSlide(AppIntroFragment.createInstance(
            "Shorts (Μικρά Βίντεο)",
            "Στο Μενού των Shorts (Μικρά Βίντεο)\n" +
                "Μπορείτε να πατήσετε κουμπί πηγή στα δεξιά\n" +
                "Για να αντιγράψετε την πηγή αυτού του βίντεο",
            R.drawable.tut7,
            R.color.part1
        ));
        addSlide(AppIntroFragment.createInstance(
            "Μαθήματα",
            "Στο Μενού των μαθημάτων\n" +
                "Μπορείτε να πατήσετε όποια κατηγορία σας ενδιαφέρει\n" +
                "Και θα σας βγάλει μία λίστα με βίντεο",
            R.drawable.tut8,
            R.color.part1
        ));
        addSlide(AppIntroFragment.createInstance(
            "Μαθήματα",
            "Αφού Διαλέξατε μία κατηγορία\n" +
                "Μπορείτε να πατήσετε ένα από τα εικονίδια\n" +
                "Για να δείτε το βίντεο",
            R.drawable.tut9,
            R.color.part1
        ));
        setTransformer(AppIntroPageTransformerType.Flow.INSTANCE);
        setIndicatorEnabled(true);
        setColorTransitionsEnabled(true);
        setImmersive(false);
        showStatusBar(true);
        setIndicatorColor(getColor(android.R.color.holo_red_light), getColor(android.R.color.holo_blue_bright));
    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment)
    {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment)
    {
        super.onDonePressed(currentFragment);
        finish();
    }
}
