package com.example.myapplication

import ItemModel
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.File
import java.io.OutputStream

class home : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawerLayout)

        val drawerNavView = findViewById<NavigationView>(R.id.drawerNavigationView)
        drawerNavView.setNavigationItemSelectedListener(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Dashboard()).commit()
            drawerNavView.setCheckedItem(R.id.nav_home)

            openDashboardFragment()
        }

        val searchButton = findViewById<FloatingActionButton>(R.id.searchBtn)

        searchButton.setOnClickListener {
            val intent = Intent(this, search::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Logic to handle Home menu item
                    openDashboardFragment()
                    true
                }
                R.id.addItem -> {
                    // Logic to handle Add Item menu item
                    openAddItemFragment()
                    true
                }
                else -> false
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Dashboard()).commit()
            R.id.nav_addItem -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddItem()).commit()
            R.id.nav_searchItem -> {
                val intent = Intent(this, search::class.java)
                startActivity(intent)
            }
            R.id.nav_export -> {
                exportData()
            }
            R.id.nav_info -> {
                val intent = Intent(this, info::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> showLogoutConfirmationDialog()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    private var databaseData: List<ItemModel>? = null

    private fun exportData() {
        val firebaseHelper = FirebaseHelper()
        firebaseHelper.fetchItemData { data ->
            if (data.isNotEmpty()) {
                databaseData = data
                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "application/pdf"
                intent.putExtra(Intent.EXTRA_TITLE, "AllSpareParts.pdf")
                startActivityForResult(intent, CREATE_PDF_REQUEST_CODE)
            } else {
                Toast.makeText(this, "No data available to export", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateAndSavePdf(outputStream: OutputStream) {
        try {
            val pdfWriter = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)

            document.add(Paragraph("All Spare Parts:"))
            databaseData?.let { data ->
                for (item in data) {
                    document.add(
                        Paragraph(
                            "SNo: ${item.sNo}\n" +
                                    "Item: ${item.item}\n" +
                                    "Current Location: ${item.currentLocation}\n" +
                                    "New Location: ${item.newLocation}\n" +
                                    "Status: ${item.status}\n" +
                                    "Unit: ${item.unit}\n\n"
                        )
                    )
                }
            }


            document.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to generate PDF", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_PDF_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                val outputStream = contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    databaseData?.let {
                        generateAndSavePdf(outputStream)
                    }
                    outputStream.close()
                } else {
                    Toast.makeText(this, "Failed to open output stream", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    companion object {
        private const val CREATE_PDF_REQUEST_CODE = 123
    }


    private fun sharePdfFile() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"
        val uri = FileProvider.getUriForFile(this, "${packageName}.provider", ExportedFile)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        // Check if there's an app to handle the Intent
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            // Handle case where no app can handle the Intent
            Toast.makeText(this, "No app found to handle PDF", Toast.LENGTH_SHORT).show()
        }
    }



    private val ExportedFile: File
        get() {
            val directory = getExternalFilesDir(null)
            return File(directory, "AllSpareParts.pdf")
        }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ -> logoutUser() }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun logoutUser() {
        val intent = Intent(this, login::class.java)
        startActivity(intent)
        finish() // Close the current activity
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            showLogoutConfirmationDialog()
        }
    }

    private fun openAddItemFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace FragmentAddItem() with your actual fragment instance
        val fragment = AddItem()

        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }

    private fun openDashboardFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace FragmentAddItem() with your actual fragment instance
        val fragment = Dashboard()

        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }
}
