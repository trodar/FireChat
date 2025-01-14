package com.trodar.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "1_phone", device = "spec:width=360dp,height=640dp,dpi=480", showBackground = true, apiLevel = 33)
@Preview(name = "2_phone_dark", uiMode = Configuration.UI_MODE_NIGHT_YES, device = "spec:width=360dp,height=640dp,dpi=480", showBackground = true, apiLevel = 34)
@Preview(name = "3_landscape", device = "spec:width=640dp,height=360dp,dpi=480", showBackground = true, apiLevel = 34)
@Preview(name = "4_foldable", device = "spec:width=673dp,height=841dp,dpi=480", showBackground = true, apiLevel = 34)
@Preview(name = "5_tablet", device = "spec:width=1280dp,height=800dp,dpi=480", showBackground = true, apiLevel = 34)
annotation class DevicePreviews
