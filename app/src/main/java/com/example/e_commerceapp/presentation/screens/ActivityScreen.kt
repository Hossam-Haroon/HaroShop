package com.example.e_commerceapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.ScreenHeadSectionComponent
import com.example.e_commerceapp.presentation.components.ColorfulButton
import com.example.e_commerceapp.presentation.components.DynamicChartForOrdersComponent
import com.example.e_commerceapp.presentation.components.MyActivityOrdersStatsCircleShapeComponent
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.Grey2
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.utils.Utils.generateDistinctColors
import com.example.e_commerceapp.presentation.viewmodels.ActivityScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActivityScreen(innerNavController: NavController) {
    val activityViewModel: ActivityScreenViewModel = hiltViewModel()
    val userOrdersStats by activityViewModel.categoryTotals.collectAsState()
    val ordersSize by activityViewModel.ordersSize.collectAsState()
    val userData by activityViewModel.userData.collectAsState()
    val receivedOrders by activityViewModel.receivedOrdersSize.collectAsState()
    val toReceiveOrders by activityViewModel.toReceiveOrdersSize.collectAsState()
    val categoryList = userOrdersStats.keys.toList()
    val colors = generateDistinctColors(categoryList.size)
    val categoryColors = categoryList.zip(colors).toMap()

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        ScreenHeadSectionComponent(userData?.imageUrl,"My Activity"){
            innerNavController.navigate(Screen.VoucherScreen.route)
        }
        Spacer(modifier = Modifier.height(60.dp))
        if(userOrdersStats.isEmpty()){
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "No Orders At All",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
        }else{
            DynamicChartForOrdersComponent(data = userOrdersStats, categoryColors = categoryColors)
        }
        Spacer(modifier = Modifier.height(15.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            userOrdersStats.forEach { (label, price) ->
                val color = categoryColors[label] ?: Grey2
                Box(
                    modifier = Modifier
                        .background(color, shape = RoundedCornerShape(50))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            label,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontFamily = raleWay,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$${price}",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontFamily = raleWay,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyActivityOrdersStatsCircleShapeComponent(
                ordersSize.toString(),
                "ordered"
            )
            MyActivityOrdersStatsCircleShapeComponent(
                receivedOrders.toString(),
                "Received"
            )
            MyActivityOrdersStatsCircleShapeComponent(
                toReceiveOrders.toString(),
                "ToReceive"
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        ColorfulButton(
            text = "Order History",
            width = 335.dp,
            height = 40.dp,
            backgroundColor = DarkBlue,
            textSize = 16.sp,
            textColor = GrayishWhite
        ) {
            innerNavController.navigate(Screen.HistoryScreen.route)
        }
    }
}

