package com.huroofna.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import android.speech.tts.TextToSpeech;
import java.util.*;

public class MainActivity extends Activity {

    TextToSpeech tts;
    LinearLayout root, content;
    int stars = 0;

    final String[] letters = {
        "ا","ب","ت","ث","ج","ح","خ","د","ذ","ر","ز","س","ش",
        "ص","ض","ط","ظ","ع","غ","ف","ق","ك","ل","م","ن","ه","و","ي"
    };

    final String[] words = {
        "باب","بيت","ماما","موز","نار","سور","قلم","كتاب"
    };

    final String[] sentences = {
        "هذا باب.",
        "ماما تقرأ.",
        "أنا أحب المدرسة.",
        "هذا قلم."
    };

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        getWindow().getDecorView()
                .setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        stars = getPreferences(0).getInt("stars", 0);

        tts = new TextToSpeech(this, s -> {});

        showHome();
    }

    TextView tv(String text, int size) {
        TextView v = new TextView(this);
        v.setText(text);
        v.setTextSize(size);
        v.setGravity(Gravity.CENTER);
        v.setTextColor(Color.rgb(40,40,55));
        v.setPadding(12,16,12,16);
        return v;
    }

    Button btn(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(18);
        b.setAllCaps(false);
        b.setPadding(12,10,12,10);
        return b;
    }

    void base(String title) {

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(18,18,18,18);
        root.setBackgroundColor(Color.rgb(250,249,246));

        TextView h = tv(title, 28);
        h.setTypeface(null, Typeface.BOLD);

        root.addView(
            h,
            new LinearLayout.LayoutParams(-1,-2)
        );

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

        ScrollView scroll = new ScrollView(this);
        scroll.addView(content);

        root.addView(
            scroll,
            new LinearLayout.LayoutParams(-1,0,1)
        );

        setContentView(root);
    }

    void add(String text, int size) {
        content.addView(
            tv(text,size),
            new LinearLayout.LayoutParams(-1,-2)
        );
    }

    void nav() {
        Button back = btn("↩ العودة للرئيسية");
        back.setOnClickListener(v -> showHome());

        root.addView(
            back,
            new LinearLayout.LayoutParams(-1,-2)
        );
    }

    void speak(String text) {
        if (tts != null) {
            tts.speak(
                text,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "huroofna"
            );
        }
    }

    void addStar() {
        stars++;

        getPreferences(0)
            .edit()
            .putInt("stars",stars)
            .apply();
    }

    void showHome() {

        base("🌟 حروفنا");

        add("من الحرف إلى القراءة والكتابة",20);
        add("⭐ النجوم: " + stars,20);

        String[] menu = {
            "🔤 الحروف والأصوات",
            "َ ُ ِ الحركات والمد",
            "🧩 الكلمات",
            "📖 الجمل والفهم",
            "🎮 لعبة تمييز الحرف",
            "✍️ الكتابة بالإصبع",
            "📝 الاختبار التشخيصي",
            "📊 تقدمي"
        };

        for (String item : menu) {

            Button b = btn(item);

            content.addView(b);

            if (item.startsWith("🔤"))
                b.setOnClickListener(v -> letters());

            else if (item.startsWith("َ"))
                b.setOnClickListener(v -> harakat());

            else if (item.startsWith("🧩"))
                b.setOnClickListener(v -> words());

            else if (item.startsWith("📖"))
                b.setOnClickListener(v -> sentences());

            else if (item.startsWith("🎮"))
                b.setOnClickListener(v -> game());

            else if (item.startsWith("✍"))
                b.setOnClickListener(v -> writing());

            else if (item.startsWith("📝"))
                b.setOnClickListener(v -> test());

            else
                b.setOnClickListener(v -> progress());
        }
    }

    void letters() {

        base("🔤 الحروف الهجائية");

        add("اضغط على الحرف لسماع صوته",18);

        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(4);

        for (String letter : letters) {

            Button b = btn(letter);
            b.setTextSize(30);

            b.setOnClickListener(v -> {
                speak(letter);
                addStar();
                showLetter(letter);
            });

            grid.addView(
                b,
                new ViewGroup.LayoutParams(0,110)
            );
        }

        content.addView(grid);

        nav();
    }

    void showLetter(String letter) {

        base("حرف " + letter);

        add(letter,72);

        add("اسم الحرف: " + letter,22);

        add("أشكال الحرف للتدريب",18);

        add(
            letter + "ـ   ـ" + letter + "   " + letter,
            30
        );

        Button sound = btn("🔊 اسمع");

        sound.setOnClickListener(
            v -> speak(letter)
        );

        content.addView(sound);

        add(
            "الحركات: " +
            letter + "َ   " +
            letter + "ُ   " +
            letter + "ِ",
            28
        );

        add(
            "المد: " +
            letter + "ا   " +
            letter + "و   " +
            letter + "ي",
            28
        );

        nav();
    }

    void harakat() {

        base("َ ُ ِ الحركات والمد");

        add(
            "فتحة  َ     ضمة  ُ     كسرة  ِ",
            30
        );

        String[] practice = {"ب","م","س","ن"};

        for (String letter : practice) {

            add(
                letter + "َ   " +
                letter + "ُ   " +
                letter + "ِ     |     " +
                letter + "ا   " +
                letter + "و   " +
                letter + "ي",
                28
            );
        }

        Button sound = btn("🔊 اسمع التدريب");

        sound.setOnClickListener(
            v -> speak("بَ بُ بِ با بو بي")
        );

        content.addView(sound);

        nav();
    }

    void words() {

        base("🧩 الكلمات");

        add("اقرأ الكلمة ثم اسمعها",20);

        for (String word : words) {

            Button b = btn("🔊 " + word);
            b.setTextSize(25);

            b.setOnClickListener(v -> {
                speak(word);
                addStar();
            });

            content.addView(b);
        }

        nav();
    }

    void sentences() {

        base("📖 الجمل والفهم");

        for (String sentence : sentences) {

            add("📖 " + sentence,25);

            Button b = btn("🔊 اسمع الجملة");

            b.setOnClickListener(
                v -> speak(sentence)
            );

            content.addView(b);

            add(
                "سؤال: هل قرأت الجملة؟",
                17
            );
        }

        nav();
    }

    void game() {

        base("🎮 لعبة تمييز الحرف");

        Random random = new Random();

        String target =
            letters[random.nextInt(letters.length)];

        add(
            "اختر الحرف: " + target,
            32
        );

        ArrayList<String> options =
            new ArrayList<>();

        options.add(target);

        while (options.size() < 4) {

            String x =
                letters[random.nextInt(letters.length)];

            if (!options.contains(x))
                options.add(x);
        }

        Collections.shuffle(options);

        for (String option : options) {

            Button b = btn(option);
            b.setTextSize(30);

            b.setOnClickListener(v -> {

                if (option.equals(target)) {

                    addStar();

                    Toast.makeText(
                        this,
                        "أحسنت! ⭐",
                        Toast.LENGTH_SHORT
                    ).show();

                } else {

                    Toast.makeText(
                        this,
                        "حاول مرة أخرى",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            });

            content.addView(b);
        }

        nav();
    }

    void writing() {

        base("✍️ أكتب الحرف");

        add(
            "اكتب بإصبعك داخل المربع",
            18
        );

        DrawView draw = new DrawView();

        content.addView(
            draw,
            new LinearLayout.LayoutParams(-1,520)
        );

        Button clear = btn("مسح");

        clear.setOnClickListener(
            v -> draw.clear()
        );

        content.addView(clear);

        Button done = btn("أنهيت التدريب ⭐");

        done.setOnClickListener(v -> {

            addStar();

            Toast.makeText(
                this,
                "رائع! ⭐",
                Toast.LENGTH_SHORT
            ).show();
        });

        content.addView(done);

        nav();
    }

    void test() {

        base("📝 الاختبار التشخيصي");

        add("1) اختر حرف (ب):",22);

        String[] choices = {"ب","ت","ن"};

        for (String choice : choices) {

            Button b = btn(choice);

            b.setOnClickListener(v -> {

                if (choice.equals("ب")) {

                    addStar();

                    Toast.makeText(
                        this,
                        "صحيح ⭐",
                        Toast.LENGTH_SHORT
                    ).show();

                } else {

                    Toast.makeText(
                        this,
                        "حاول مرة أخرى",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            });

            content.addView(b);
        }

        add("2) اقرأ: باب",22);

        add(
            "3) اختر الحركة الصحيحة: بَ",
            22
        );

        Button finish = btn("✓ أنهيت الاختبار");

        finish.setOnClickListener(v -> {

            addStar();

            Toast.makeText(
                this,
                "تم تسجيل التقدم ⭐",
                Toast.LENGTH_SHORT
            ).show();
        });

        content.addView(finish);

        nav();
    }

    void progress() {

        base("📊 تقدمي");

        add(
            "النجوم الحالية: ⭐ " + stars,
            28
        );

        add(
            "تقدمك محفوظ على هذا الهاتف.",
            19
        );

        add(
            "كرر الحروف ثم الحركات ثم الكلمات والجمل.",
            19
        );

        nav();
    }

    class DrawView extends View {

        Paint paint = new Paint();
        Path path = new Path();

        DrawView() {

            super(MainActivity.this);

            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.DKGRAY);

            setBackgroundColor(Color.WHITE);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawPath(path,paint);
        }

        @Override
        public boolean onTouchEvent(
            MotionEvent event
        ) {

            float x = event.getX();
            float y = event.getY();

            if (event.getAction() ==
                MotionEvent.ACTION_DOWN) {

                path.moveTo(x,y);

            } else if (
                event.getAction() ==
                MotionEvent.ACTION_MOVE) {

                path.lineTo(x,y);
            }

            invalidate();

            return true;
        }

        void clear() {

            path.reset();
            invalidate();
        }
    }

    @Override
    protected void onDestroy() {

        if (tts != null) {

            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }
}
