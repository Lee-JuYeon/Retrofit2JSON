package com.universeindustry.retrofit2json.ui.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/*
  @JvmStatic = BindingAdapter는 Java의 static 함수로 되어 있기 때문에 @JvmStatic 어노테이션을 붙여줘야 한다.
  @BindingAdapter에는 2개의 field가 있다.
     1. value
        - xml에서 사용하고 싶은 속성.
          profileUrl 속성 값으로 String, profilePlaceHolder 속성 값으로 Drawable값을 받아온다.
     2. requireAll
        - bindingAdapter를 사용할 때 모든 속성을 사용할 지에 대한 값이다.
          false일 경우에 placeHolder 값이 없다고 해도 오류를 발생하지 않는다.
          true가 기본 값이기 때문에 별도로 설정을 해주지 않으면 반드시 모든 값을 연결해줘야 한다.

     이런식으로  Image.loadImage 처럼 확장함수를 사용하면 @JvmStatic 어노테이션을 붙이지 않아도 되며 object로 감싸지 않아도 된다.

   */

//    @JvmStatic
@BindingAdapter(
    value = ["imageURL"],
    requireAll = false
)
fun ImageView.loadImage(imageURL : String?){
    Glide.with(context)
        .load(imageURL)
        .into(this)
}