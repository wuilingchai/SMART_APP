package com.bignerdranch.android.smart;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.text.format.DateFormat;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bignerdranch.android.smart.database.SmartBaseHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class SmartFragment extends Fragment {

    private static final String ARG_SMART_ID = "smart_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";


    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_GALLERY = 2;
    private static final int REQUEST_PHOTO= 3;

    private Smart mSmart;
    private File mPhotoFile;
    private EditText mTitleField;
    private EditText mSpecificField;
    private EditText mMeasureableField;
    private EditText mAttainableField;
    private EditText mRelevantField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mCompletedCheckBox;
    private Button mGalleryButton;
    private Button mGoalButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;



    String newDate;
    String newTime;


    public static SmartFragment newInstance (UUID smartId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_SMART_ID, smartId);

        SmartFragment fragment = new SmartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID smartId = (UUID) getArguments().getSerializable(ARG_SMART_ID);
        mSmart = SmartLab.get(getActivity()).getSmart(smartId);
        mPhotoFile = SmartLab.get(getActivity()).getPhotoFile(mSmart);
    }

    @Override
    public void onPause(){
        super.onPause();

        SmartLab.get(getActivity())
                .updateGoals(mSmart);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_smart, container, false);

//        mSmartRecyclerView1 = (RecyclerView) v
//                .findViewById(R.id.smart_recycler_detailview);
//        mSmartRecyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTitleField = (EditText) v.findViewById(R.id.smart_title);
        mTitleField.setText(mSmart.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //this space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSmart.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //this left blank
            }
        });

        mSpecificField = (EditText) v.findViewById(R.id.smart_specific);
        mSpecificField.setText(mSmart.getSpecific());
        mSpecificField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //this space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSmart.setSpecific(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //this left blank
            }
        });

        mMeasureableField = (EditText) v.findViewById(R.id.smart_measuarable);
        mMeasureableField.setText(mSmart.getMeasurable());
        mMeasureableField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //this space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSmart.setMeasurable(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //this left blank
            }
        });

        mAttainableField = (EditText) v.findViewById(R.id.smart_attainable);
        mAttainableField.setText(mSmart.getAttainable());
        mAttainableField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //this space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSmart.setAttainable(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //this left blank
            }
        });

        mRelevantField = (EditText) v.findViewById(R.id.smart_relevant);
        mRelevantField.setText(mSmart.getRelevant());
        mRelevantField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //this space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSmart.setRelevant(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //this left blank
            }
        });

        mDateButton = (Button) v.findViewById(R.id.smart_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mSmart.getDate());
                dialog.setTargetFragment(SmartFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton = (Button) v.findViewById(R.id.smart_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(mSmart.getTime());
                dialog.setTargetFragment(SmartFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });


        mCompletedCheckBox = (CheckBox) v.findViewById(R.id.smart_completed);
        mCompletedCheckBox.setChecked(mSmart.isCompleted());
        mCompletedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSmart.setCompleted(isChecked);
            }
        });

        mGoalButton = (Button) v.findViewById(R.id.smart_summary);
        mGoalButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getGoalReport());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.smart_goal_subject));
                i = Intent.createChooser(i, getString(R.string.send_goal));
                startActivity(i);
            }
        });

        final Intent pickGallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Intent pickGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        mGalleryButton = (Button) v.findViewById(R.id.smart_gallery);
        mGalleryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(pickGallery, REQUEST_GALLERY);
            }
        });

        if (mSmart.getGallery() != null){
            mGalleryButton.setText(mSmart.getGallery());
        }

        PackageManager packageManager =getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickGallery,
                PackageManager.MATCH_DEFAULT_ONLY) == null){
            mGalleryButton.setEnabled(false);
        }

        mPhotoButton = (ImageButton) v.findViewById(R.id.smart_camera);
        final Intent captureImage = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.bignerdranch.android.smart.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities){
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.take_photo);
        updatePhotoView();


        return v;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_DATE){
                Date date = (Date) data
                        .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mSmart.setDate(date);
                updateDate();

        } else if (requestCode == REQUEST_TIME) {
            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mSmart.setTime(date);
            updateTime();
            Toast.makeText(getActivity(), mSmart.getTime() + "Time", Toast.LENGTH_SHORT).show();

        }else if (requestCode == REQUEST_GALLERY && data != null){
            Uri selectedImage = data.getData();
            mPhotoView.setImageURI(selectedImage);

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor c = getActivity().getContentResolver()
                .query(selectedImage, filePathColumn, null, null, null);

            try{
                if (c.getCount() == 0){
                    return;
                }

                c.moveToFirst();
                String gallery = c.getString(0);
                mSmart.setGallery(gallery);
                mGalleryButton.setText(gallery);
            }finally {
                c.close();

            }
            }else if (requestCode == REQUEST_PHOTO){
                Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.bignerdranch.android.smart.fileprovider",
                    mPhotoFile);

             getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }

    }

    private void updateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd'/'MM'/'yyyy");
        newDate = simpleDateFormat.format(mSmart.getDate());
        mDateButton.setText(newDate);

    }

    private void updateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        newTime = simpleDateFormat.format(mSmart.getTime());
        mTimeButton.setText(newTime);
    }


    private String getGoalReport(){
        String completedString = null;
        if (mSmart.isCompleted()) {
            completedString = getString(R.string.smart_goal_completed);
        }else {
            completedString = getString(R.string.smart_goal_incompleted);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat,
                                                mSmart.getDate()).toString();

        String gallery = mSmart.getGallery();
        if (gallery == null){
            gallery = getString(R.string.smart_goal_no_photo);
        }else{
            gallery = getString(R.string.smart_goal_photo, gallery);
        }

        String goal = getString(R.string.smart_goal,
                mSmart.getTitle(), dateString, completedString, gallery);

        return goal;
    }

    private void updatePhotoView(){
        if (mPhotoFile == null || !mPhotoFile.exists()){
            mPhotoView.setImageDrawable(null);

        }else{
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
            mGalleryButton.setText(R.string.smart_gallery_text);
        }
    }

}
