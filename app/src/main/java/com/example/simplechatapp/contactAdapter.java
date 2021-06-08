package com.example.simplechatapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.io.File;
import java.util.List;


// RecyclerView Adapter for use with the list of Contacts
public class contactAdapter extends RecyclerView.Adapter<contactAdapter.contactViewHolder> {

    private final List<contact> contacts; // the list of contacts
    private static Context context = null; // reference to the activity context
    private int curPosition = -1; // current position selected in the list
    private Object contact;

    // Initialisation
    public contactAdapter(Context context, List<contact> contacts) {
        this.contacts = contacts;
        this.context = context;
    }


    @NonNull
    @Override
    public contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new contactViewHolder(view);
    }

    // Binds details and event handlers to each viewholder
    @Override
    public void onBindViewHolder(@NonNull contactViewHolder holder, int position) {
        // Setting of contact in holder
        contact journal = contacts.get(position);
        holder.setContact((com.example.simplechatapp.contact) journal);
        // Find references to floating buttons on this specific contact view in the recyclerview
        View v = holder.itemView;

    }

    // Returns the size of the contact list
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    // Adds a new contact to the end of the list
    public void add(contact contact) {
        contacts.add(contact);
        notifyItemChanged(contacts.size() - 1);
    }

    // Removes the contact at the specific position
    public void remove(int position) {
        contacts.remove(position);
        curPosition = -1;
        notifyDataSetChanged();
    }

    // Returns the list of contacts
    public List<contact> getList() {
        return contacts;
    }

    // View holder for recyclerview to recycler contacts into
    public static class contactViewHolder extends RecyclerView.ViewHolder {
        // References to views
        TextView lblID;
        TextView lblLastText;
        ImageView imageTemp;
        Dialog dialog;

        public contactViewHolder(@NonNull View itemView) {
            super(itemView);
            lblID = itemView.findViewById(R.id.contactIDtext);
            lblLastText = itemView.findViewById(R.id.contactLastText);
            imageTemp = itemView.findViewById(R.id.imageAvatar);

        }

        // Set views to current contact's values
        void setContact(final contact contact) {
            View.OnClickListener OpenPicture = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(contact.getID(),contact.getImagePath());
                }
            };
            View.OnClickListener OpenChats = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,conversation.class);
                    intent.putExtra("user",(Parcelable)new contact(contact.getID(),contact.lastText,contact.imgPath));
                    context.startActivity(intent);
                }
            };
            lblID.setOnClickListener(OpenChats);
            lblLastText.setOnClickListener(OpenChats);
            imageTemp.setOnClickListener(OpenPicture);
            lblID.setText(contact.getID());
            lblLastText.setText(contact.getLastText());
            // Get the dimensions of the View
            int targetW = imageTemp.getWidth();
            int targetH = imageTemp.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(contact.getImagePath(), bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            //int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            //bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            String filePath = contact.getImagePath();
            File imgFile = new File(filePath);
            if (imgFile.exists()) {
                Bitmap icon = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                //Drawable d = new BitmapDrawable(getResources(), myBitmap);
                //Bitmap icon =drawableToBitmap(drawable);
                imageTemp.setImageBitmap(icon);
            }
            else
            {
                Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.maleavatar);
                imageTemp.setImageBitmap(icon);
            }
        }

        public String getSafeSubstring(String s, int maxLength) {
            if (!TextUtils.isEmpty(s)) {
                if (s.length() >= maxLength) {
                    return s.substring(0, maxLength);
                }
            }
            return s;
        }

        public static Bitmap drawableToBitmap(Drawable drawable) {
            Bitmap bitmap = null;

            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }

            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        private void showDialog(String name,String ImageURL) {
            // custom dialog
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.viewprofilepicture);

            // set the custom dialog components - text, image and button
            ImageButton close = (ImageButton) dialog.findViewById(R.id.imageButtonClose);
            TextView Name = (TextView) dialog.findViewById(R.id.textView8);
            ImageView ProfilePicture = (ImageView) dialog.findViewById(R.id.imageView4);
            Name.setText(name);

            // Get the dimensions of the View
            int targetW = ProfilePicture.getWidth();
            int targetH = ProfilePicture.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(ImageURL, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            //int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            //bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            String filePath = ImageURL;
            File imgFile = new File(filePath);
            if (imgFile.exists()) {
                Bitmap icon = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                //Drawable d = new BitmapDrawable(getResources(), myBitmap);
                //Bitmap icon =drawableToBitmap(drawable);
                ProfilePicture.setImageBitmap(icon);
            }
            // Close Button
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    //TODO Close button action
                }
            });

            /** Buy Button
             buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dialog.dismiss();
            //TODO Buy button action
            }
            });*/

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();
        }
    }

}

