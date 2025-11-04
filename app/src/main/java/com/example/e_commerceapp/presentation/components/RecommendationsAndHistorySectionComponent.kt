package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.SearchScreenViewModel

@Composable
fun RecommendationsAndHistorySectionComponent(
    requiredToApplyList: List<String>,
    searchViewmodel: SearchScreenViewModel
) {
    Column {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (requiredToApplyList.isNotEmpty()){
                for (item in 0..2) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .background(GrayishWhite)
                            .clickable { searchViewmodel.onChangeQuery(requiredToApplyList[item]) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = requiredToApplyList[item],
                            fontFamily = raleWay,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (requiredToApplyList.isNotEmpty()){
                for (item in 3..4) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .background(GrayishWhite)
                            .clickable { searchViewmodel.onChangeQuery(requiredToApplyList[item]) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = requiredToApplyList[item],
                            fontFamily = raleWay,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}