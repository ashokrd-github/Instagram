package com.yaduvanshi_ashok_rd.instagramclone.Model

import android.widget.ImageView

class post {
   var postedImage: String? =null
     var Description: String? =null

     constructor(){}
     constructor(Description: String, postedImage: ImageView) {
         this.postedImage = postedImage.toString()
         this.Description = Description

     }


 }

