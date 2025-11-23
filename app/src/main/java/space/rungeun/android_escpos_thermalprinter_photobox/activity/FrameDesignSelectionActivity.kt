package space.rungeun.android_escpos_thermalprinter_photobox.activity

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import space.rungeun.android_escpos_thermalprinter_photobox.R
import space.rungeun.android_escpos_thermalprinter_photobox.controller.FrameRecyclerViewAdapter
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityFrameDesignSelectionBinding
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityFrameSelectionBinding

class FrameDesignSelectionActivity : AppCompatActivity() {
    private lateinit var biding : ActivityFrameDesignSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivityFrameDesignSelectionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(biding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        biding.frameRecyclerView.layoutManager = LinearLayoutManager(this)
        biding.frameRecyclerView.layoutManager = GridLayoutManager(this, 3)
        biding.frameRecyclerView.adapter = FrameRecyclerViewAdapter()

        biding.toPrivacyPolicyConsentButton.setOnClickListener {
            val intent = Intent(this, PrivacyPolicyConsentActivity::class.java)
            startActivity(intent) }
    }
}