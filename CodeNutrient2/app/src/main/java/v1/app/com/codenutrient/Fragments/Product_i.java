package v1.app.com.codenutrient.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.Fragment.SavedState;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.transition.Transition;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.tools.fd.runtime.IncrementalChange;
import com.android.tools.fd.runtime.InstantReloadException;
import com.squareup.picasso.Picasso;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import v1.app.com.codenutrient.C0321R;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.Constants;
import v1.app.com.codenutrient.POJO.Product;

public class Product_i extends Fragment {
    public static volatile transient /* synthetic */ IncrementalChange $change;
    public Button evaluar;
    public ImageView imageView;
    private OnClickListener onClickListener;
    public TextView presentacion;
    public TextView product_calories;
    public TextView product_name;
    public TextView product_portions;
    public ProgressBar progressBar;
    public Button registrar;
    public View rootView;

    /* renamed from: v1.app.com.codenutrient.Fragments.Product_i.1 */
    class C03201 implements OnClickListener {
        public static volatile transient /* synthetic */ IncrementalChange $change;
        public final /* synthetic */ Product_i this$0;

        C03201(Object[] objArr, InstantReloadException instantReloadException) {
            switch (((String) objArr[0]).hashCode()) {
                case -1968665286:
                case 1744506665:
                    this((Product_i) objArr[1]);
                default:
                    throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Fragments/Product_i$1"}));
            }
        }

        public static /* synthetic */ Object access$super(C03201 c03201, String str, Object... objArr) {
            switch (str.hashCode()) {
                case -2128160755:
                    return super.toString();
                case -1600833221:
                    super.wait(((Number) objArr[0]).longValue(), ((Number) objArr[1]).intValue());
                    return null;
                case -1554832987:
                    super.finalize();
                    return null;
                case -1166127280:
                    super.notify();
                    return null;
                case -1021472056:
                    super.wait(((Number) objArr[0]).longValue());
                    return null;
                case -712101345:
                    super.notifyAll();
                    return null;
                case 201261558:
                    return super.getClass();
                case 244142972:
                    super.wait();
                    return null;
                case 1403628309:
                    return new Integer(super.hashCode());
                case 1814730534:
                    return new Boolean(super.equals(objArr[0]));
                case 2025021518:
                    return super.clone();
                default:
                    throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Fragments/Product_i$1"}));
            }
        }

        public void onClick(View view) {
            IncrementalChange incrementalChange = $change;
            if (incrementalChange != null) {
                incrementalChange.access$dispatch("onClick.(Landroid/view/View;)V", this, view);
                return;
            }
            switch (view.getId()) {
                case C0321R.id.Evaluate /*2131624162*/:
                    Toast.makeText(this.this$0.getActivity().getApplicationContext(), "Te recomendamos consumir este producto dado su alto contenido de calcio", 0).show();
                case C0321R.id.register /*2131624163*/:
                    Toast.makeText(this.this$0.getActivity().getApplicationContext(), "Se ha registrado el producto como consumido", 0).show();
                default:
            }
        }

        public C03201(Product_i this$0) {
            IncrementalChange incrementalChange = $change;
            this.this$0 = this$0;
            if (incrementalChange != null) {
                Object[] objArr = new Object[]{objArr, this$0};
                this$0 = objArr[1];
                this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;Lv1/app/com/codenutrient/Fragments/Product_i;)Ljava/lang/Object;", objArr), null);
            }
            if (incrementalChange != null) {
                incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Fragments/Product_i$1;Lv1/app/com/codenutrient/Fragments/Product_i;)V", this, this$0);
            }
        }
    }

    Product_i(Object[] objArr, InstantReloadException instantReloadException) {
        switch (((String) objArr[0]).hashCode()) {
            case -947953723:
            case -739038519:
                this();
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{(String) objArr[0], Integer.valueOf(((String) objArr[0]).hashCode()), "v1/app/com/codenutrient/Fragments/Product_i"}));
        }
    }

    public static /* synthetic */ Object access$super(Product_i product_i, String str, Object... objArr) {
        switch (str.hashCode()) {
            case -2147180915:
                super.onSaveInstanceState((Bundle) objArr[0]);
                return null;
            case -2143386509:
                super.setSharedElementEnterTransition((Transition) objArr[0]);
                return null;
            case -2128461924:
                return super.getActivity();
            case -2128160755:
                return super.toString();
            case -2116008609:
                super.startActivity((Intent) objArr[0], (Bundle) objArr[1]);
                return null;
            case -2051374976:
                return super.getChildFragmentManager();
            case -1963282844:
                return super.getReturnTransition();
            case -1848759567:
                super.setRetainInstance(((Boolean) objArr[0]).booleanValue());
                return null;
            case -1824869764:
                super.onPrepareOptionsMenu((Menu) objArr[0]);
                return null;
            case -1749129850:
                super.onMultiWindowModeChanged(((Boolean) objArr[0]).booleanValue());
                return null;
            case -1630383126:
                super.startActivityForResult((Intent) objArr[0], ((Number) objArr[1]).intValue(), (Bundle) objArr[2]);
                return null;
            case -1600833221:
                super.wait(((Number) objArr[0]).longValue(), ((Number) objArr[1]).intValue());
                return null;
            case -1600818538:
                return new Boolean(super.isRemoving());
            case -1597737654:
                super.unregisterForContextMenu((View) objArr[0]);
                return null;
            case -1589549411:
                super.onAttach((Context) objArr[0]);
                return null;
            case -1554832987:
                super.finalize();
                return null;
            case -1512649357:
                super.onResume();
                return null;
            case -1504501726:
                super.onDestroy();
                return null;
            case -1486578856:
                return new Boolean(super.isResumed());
            case -1483807804:
                return super.getString(((Number) objArr[0]).intValue(), (Object[]) objArr[1]);
            case -1348923845:
                return new Boolean(super.getRetainInstance());
            case -1280669581:
                return new Boolean(super.isVisible());
            case -1166127280:
                super.notify();
                return null;
            case -1126882532:
                return super.onCreateView((LayoutInflater) objArr[0], (ViewGroup) objArr[1], (Bundle) objArr[2]);
            case -1106401236:
                super.onAttachFragment((Fragment) objArr[0]);
                return null;
            case -1078840832:
                super.setEnterTransition((Transition) objArr[0]);
                return null;
            case -1021472056:
                super.wait(((Number) objArr[0]).longValue());
                return null;
            case -1010986463:
                super.setUserVisibleHint(((Boolean) objArr[0]).booleanValue());
                return null;
            case -962742886:
                super.onTrimMemory(((Number) objArr[0]).intValue());
                return null;
            case -946171635:
                return super.getReenterTransition();
            case -936794346:
                super.onPictureInPictureModeChanged(((Boolean) objArr[0]).booleanValue());
                return null;
            case -907318214:
                super.setMenuVisibility(((Boolean) objArr[0]).booleanValue());
                return null;
            case -872444662:
                super.onViewStateRestored((Bundle) objArr[0]);
                return null;
            case -855639737:
                return new Boolean(super.isDetached());
            case -783742144:
                super.onCreateContextMenu((ContextMenu) objArr[0], (View) objArr[1], (ContextMenuInfo) objArr[2]);
                return null;
            case -712101345:
                super.notifyAll();
                return null;
            case -684027347:
                return super.getHost();
            case -641568046:
                super.onCreate((Bundle) objArr[0]);
                return null;
            case -600130598:
                return new Boolean(super.getAllowReturnTransitionOverlap());
            case -543045568:
                super.requestPermissions((String[]) objArr[0], ((Number) objArr[1]).intValue());
                return null;
            case -523441978:
                super.setEnterSharedElementCallback((SharedElementCallback) objArr[0]);
                return null;
            case -512593800:
                return super.getContext();
            case -440799600:
                return super.getFragmentManager();
            case -406251089:
                super.startIntentSenderForResult((IntentSender) objArr[0], ((Number) objArr[1]).intValue(), (Intent) objArr[2], ((Number) objArr[3]).intValue(), ((Number) objArr[4]).intValue(), ((Number) objArr[5]).intValue(), (Bundle) objArr[6]);
                return null;
            case -372450992:
                return super.getParentFragment();
            case -349229044:
                super.onConfigurationChanged((Configuration) objArr[0]);
                return null;
            case -267587217:
                return super.getSharedElementReturnTransition();
            case -174312732:
                super.setReturnTransition((Transition) objArr[0]);
                return null;
            case -129020188:
                return new Boolean(super.onContextItemSelected((MenuItem) objArr[0]));
            case -79002926:
                return super.getExitTransition();
            case -61650832:
                return super.getLoaderManager();
            case -53010187:
                return super.getSharedElementEnterTransition();
            case 26057865:
                super.onDestroyOptionsMenu();
                return null;
            case 60994803:
                super.setReenterTransition((Transition) objArr[0]);
                return null;
            case 66113232:
                super.setExitSharedElementCallback((SharedElementCallback) objArr[0]);
                return null;
            case 116272469:
                super.startActivity((Intent) objArr[0]);
                return null;
            case 179534607:
                super.dump((String) objArr[0], (FileDescriptor) objArr[1], (PrintWriter) objArr[2], (String[]) objArr[3]);
                return null;
            case 188604040:
                super.onStop();
                return null;
            case 201261558:
                return super.getClass();
            case 244142972:
                super.wait();
                return null;
            case 323816587:
                return super.getString(((Number) objArr[0]).intValue());
            case 344615528:
                super.setTargetFragment((Fragment) objArr[0], ((Number) objArr[1]).intValue());
                return null;
            case 377373002:
                return super.onCreateAnimator(((Number) objArr[0]).intValue(), ((Boolean) objArr[1]).booleanValue(), ((Number) objArr[2]).intValue());
            case 382958558:
                super.onCreateOptionsMenu((Menu) objArr[0], (MenuInflater) objArr[1]);
                return null;
            case 434397186:
                super.onHiddenChanged(((Boolean) objArr[0]).booleanValue());
                return null;
            case 462397159:
                super.onDestroyView();
                return null;
            case 500870923:
                return new Integer(super.getId());
            case 504632882:
                super.setAllowReturnTransitionOverlap(((Boolean) objArr[0]).booleanValue());
                return null;
            case 602429250:
                super.onRequestPermissionsResult(((Number) objArr[0]).intValue(), (String[]) objArr[1], (int[]) objArr[2]);
                return null;
            case 711350056:
                super.setHasOptionsMenu(((Boolean) objArr[0]).booleanValue());
                return null;
            case 776159390:
                super.setInitialSavedState((SavedState) objArr[0]);
                return null;
            case 782036510:
                return new Boolean(super.shouldShowRequestPermissionRationale((String) objArr[0]));
            case 797441118:
                super.onPause();
                return null;
            case 902425770:
                super.startActivityForResult((Intent) objArr[0], ((Number) objArr[1]).intValue());
                return null;
            case 922616583:
                return super.getResources();
            case 1002290867:
                super.onActivityCreated((Bundle) objArr[0]);
                return null;
            case 1011911513:
                super.setArguments((Bundle) objArr[0]);
                return null;
            case 1049763651:
                super.registerForContextMenu((View) objArr[0]);
                return null;
            case 1065143297:
                return super.getText(((Number) objArr[0]).intValue());
            case 1148109731:
                return new Boolean(super.getUserVisibleHint());
            case 1184509649:
                super.setSharedElementReturnTransition((Transition) objArr[0]);
                return null;
            case 1199845804:
                super.setAllowEnterTransitionOverlap(((Boolean) objArr[0]).booleanValue());
                return null;
            case 1257714799:
                super.onActivityResult(((Number) objArr[0]).intValue(), ((Number) objArr[1]).intValue(), (Intent) objArr[2]);
                return null;
            case 1270686685:
                super.onLowMemory();
                return null;
            case 1330549917:
                super.onAttach((Activity) objArr[0]);
                return null;
            case 1403628309:
                return new Integer(super.hashCode());
            case 1420141845:
                return super.getTag();
            case 1466578404:
                return super.getView();
            case 1487956297:
                return super.getTargetFragment();
            case 1677731713:
                return new Boolean(super.isAdded());
            case 1712418831:
                return new Integer(super.getTargetRequestCode());
            case 1732681771:
                return super.getArguments();
            case 1814730534:
                return new Boolean(super.equals(objArr[0]));
            case 1860817453:
                super.onViewCreated((View) objArr[0], (Bundle) objArr[1]);
                return null;
            case 1867038015:
                super.onInflate((Context) objArr[0], (AttributeSet) objArr[1], (Bundle) objArr[2]);
                return null;
            case 1876348903:
                super.onOptionsMenuClosed((Menu) objArr[0]);
                return null;
            case 1893326613:
                return new Boolean(super.onOptionsItemSelected((MenuItem) objArr[0]));
            case 1946247687:
                super.onInflate((Activity) objArr[0], (AttributeSet) objArr[1], (Bundle) objArr[2]);
                return null;
            case 1970499936:
                return super.getEnterTransition();
            case 1980512117:
                super.onInflate((AttributeSet) objArr[0], (Bundle) objArr[1]);
                return null;
            case 2025021518:
                return super.clone();
            case 2082748726:
                super.setExitTransition((Transition) objArr[0]);
                return null;
            case 2127624665:
                super.onDetach();
                return null;
            case 2133689546:
                super.onStart();
                return null;
            case 2141721784:
                return new Boolean(super.getAllowEnterTransitionOverlap());
            case 2142517759:
                return new Boolean(super.isHidden());
            case 2145209348:
                return new Boolean(super.isInLayout());
            default:
                throw new InstantReloadException(String.format("String switch could not find '%s' with hashcode %s in %s", new Object[]{str, Integer.valueOf(str.hashCode()), "v1/app/com/codenutrient/Fragments/Product_i"}));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            return (View) incrementalChange.access$dispatch("onCreateView.(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;", this, inflater, container, savedInstanceState);
        }
        this.rootView = inflater.inflate(C0321R.layout.product_i, container, false);
        this.product_name = (TextView) this.rootView.findViewById(C0321R.id.product_name);
        this.product_calories = (TextView) this.rootView.findViewById(C0321R.id.product_calories);
        this.product_portions = (TextView) this.rootView.findViewById(C0321R.id.product_portions);
        this.presentacion = (TextView) this.rootView.findViewById(C0321R.id.presentation);
        this.imageView = (ImageView) this.rootView.findViewById(C0321R.id.product_image);
        this.progressBar = (ProgressBar) this.rootView.findViewById(C0321R.id.product_progress);
        this.evaluar = (Button) this.rootView.findViewById(C0321R.id.Evaluate);
        this.registrar = (Button) this.rootView.findViewById(C0321R.id.register);
        this.evaluar.setOnClickListener(this.onClickListener);
        this.registrar.setOnClickListener(this.onClickListener);
        return this.rootView;
    }

    public void setData(Product product, Context context) {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("setData.(Lv1/app/com/codenutrient/POJO/Product;Landroid/content/Context;)V", this, product, context);
            return;
        }
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        dataBaseHelper.openDataBaseReadWrite();
        Cursor cursor = dataBaseHelper.fetchMeasure(product.getMeasure_id());
        if (cursor != null && cursor.moveToFirst()) {
            this.presentacion.setText("Presentaci\u00f3n: " + product.getCantidad() + cursor.getString(cursor.getColumnIndex("abreviacion")));
        }
        this.product_name.setText(product.getNombre());
        this.product_calories.setText("Calorias: " + product.getCalorias());
        this.product_portions.setText("Porciones: " + product.getPorcion());
        Picasso.with(context).load(Constants.ip_addr + product.getImageURL()).into(this.imageView);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.progressBar.setVisibility(4);
        this.product_name.setVisibility(0);
        this.product_calories.setVisibility(0);
        this.product_portions.setVisibility(0);
        this.imageView.setVisibility(0);
        this.presentacion.setVisibility(0);
        this.evaluar.setVisibility(0);
        this.registrar.setVisibility(0);
    }

    public Product_i() {
        IncrementalChange incrementalChange = $change;
        if (incrementalChange != null) {
            this((Object[]) incrementalChange.access$dispatch("init$args.([Ljava/lang/Object;)Ljava/lang/Object;", r2), null);
        }
        if (incrementalChange != null) {
            incrementalChange.access$dispatch("init$body.(Lv1/app/com/codenutrient/Fragments/Product_i;)V", this);
            return;
        }
        this.onClickListener = new C03201(this);
    }
}
