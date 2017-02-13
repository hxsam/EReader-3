package com.hnws.fmt.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.covics.zxingscanner.OnDecodeCompletionListener;
import com.covics.zxingscanner.ScannerView;
import com.hnws.fmt.R;

public class BarCodeScannerActivity extends BaseActivity implements
		OnDecodeCompletionListener {

	private ScannerView scannerView;
	private TextView txtResult;
	TextView titlename;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_barcode_scanner);

		scannerView = (ScannerView) findViewById(R.id.scanner_view);
		txtResult = (TextView) findViewById(R.id.txtResult);
		scannerView.setOnDecodeListener(this);
		titlename = (TextView) findViewById(R.id.titlename);
		titlename.setText("ɨһɨ");
	}

	public void onDecodeCompletion(String barcodeFormat, String barcode,
			Bitmap bitmap) {
		// TODO Auto-generated method stub
		txtResult.setText("条码制式:" + barcodeFormat + "条码:" + barcode);
		Intent mIntent = getIntent();
		mIntent.putExtra("barcode", barcode);
		setResult(RESULT_OK, mIntent);
		link(barcode);
		this.finish();
	}

	private void link(String url) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		scannerView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		scannerView.onPause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}