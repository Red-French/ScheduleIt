//package net.redfrench.scheduleit;
//
//import android.content.Context;
//import android.support.annotation.LayoutRes;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.text.Layout;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import static net.redfrench.scheduleit.R.id.timeslots;
//
//class CustomAdapter extends ArrayAdapter<String>{  // note: removed 'public'
//
//    public CustomAdapter(@NonNull Context context, @LayoutRes String[] timeslots) {  // pass timeSlots to constructor
//        super(context, R.layout.custom_row, timeslots);
//    }
//
//    @NonNull
//    @Override  // set view of the time slot list
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        // initialization
//        LayoutInflater myInflater = LayoutInflater.from(getContext());
//        View CustomView = myInflater.inflate(R.layout.custom_row, parent, false);
//
//        // get references
//        String timePosition = getItem(position);  // get position reference of each item
//        TextView myText = (TextView) CustomView.findViewById(R.id.myText);
//        ImageView myImage = (ImageView) CustomView.findViewById(R.id.myImage);
//
//        // set text and image
//        myText.setText(timePosition);
//        myImage.setImageResource(android.R.drawable.ic_delete);
//
//        return CustomView;
//    }
//}
