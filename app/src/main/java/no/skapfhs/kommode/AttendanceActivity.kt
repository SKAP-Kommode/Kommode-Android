package no.skapfhs.kommode

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


const val MOCK_STUDENT_ID = "oiu7i3i4h7r"

class AttendanceActivity : AppCompatActivity() {

    private lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        // Set the transition name, which matches Activity A’s start view transition name, on
        // the root view.
        //findViewById<ContentFrameLayout>(android.R.id.content).transitionName = "attendance_shared_container"

        // Attach a callback used to receive the shared elements from Activity A to be
        // used by the container transform transition.
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        // Set this Activity’s enter and return transition to a MaterialContainerTransform
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 300L
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 250L
        }

        setContentView(R.layout.activity_attendance)
    }

    override fun onResume() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val nfcIntent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        nfcIntent.action = NfcAdapter.ACTION_TAG_DISCOVERED
        val nfcPendingIntent = PendingIntent.getActivity(
            this,
            0,
            nfcIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, null, null)


        super.onResume()
    }

    override fun onPause() {
        nfcAdapter.disableForegroundDispatch(this)
        super.onPause()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val studentId = MOCK_STUDENT_ID

        Log.d("read tag", intent.action.toString())
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)?.let { bytesToHexString(it) }

            // Register the student in Firestore database
            val db = Firebase.firestore
            db.collection("attendance").add(mapOf(
                "studentId" to studentId,
                "roomCode" to tagId,
                "timestamp" to FieldValue.serverTimestamp()
            )).addOnSuccessListener {
                // Show success message
                Toast.makeText(this, "Attendance registered successfully!", Toast.LENGTH_SHORT).show()
                successRegistered()
            }.addOnFailureListener {
                // Show error message
                Toast.makeText(this, "Failed to register attendance!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bytesToHexString(src: ByteArray): String {
        val sb = StringBuilder()
        for (b in src) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }

    private fun successRegistered() {
        val colorFrom = resources.getColor(R.color.md_theme_dark_background, theme)
        val colorTo = resources.getColor(R.color.md_theme_dark_secondaryContainer, theme)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 250 // milliseconds

        colorAnimation.addUpdateListener { animator -> findViewById<ConstraintLayout>(R.id.content).setBackgroundColor(animator.animatedValue as Int) }

        val icon = findViewById<ImageView>(R.id.main_icon_attendacne)
        val fadeIn = ObjectAnimator.ofFloat(icon, View.ALPHA, 0f, 1f)
        val fadeOut = ObjectAnimator.ofFloat(icon, View.ALPHA, 1f, 0f)

        val set = AnimatorSet()
        set.play(fadeOut)
        set.duration = 500 // adjust duration as needed
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                icon.setImageResource(R.drawable.beenhere_48px)

                animation.
            }

        })
        set.start()


        colorAnimation.start()
    }

}