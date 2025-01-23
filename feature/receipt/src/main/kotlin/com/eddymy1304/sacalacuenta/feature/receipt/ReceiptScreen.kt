package com.eddymy1304.sacalacuenta.feature.receipt

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.eddymy1304.sacalacuenta.core.designsystem.component.SimpleLoading
import com.eddymy1304.sacalacuenta.core.designsystem.component.SimpleMenu
import com.eddymy1304.sacalacuenta.core.designsystem.theme.SacaLaCuentaTheme
import com.eddymy1304.sacalacuenta.core.model.DetailReceipt
import com.eddymy1304.sacalacuenta.core.model.Receipt
import com.eddymy1304.sacalacuenta.core.ui.DetailReceiptCard
import kotlinx.coroutines.launch
import com.eddymy1304.sacalacuenta.core.ui.R as uiR
import com.eddymy1304.sacalacuenta.core.designsystem.R as dR

@Composable
fun ReceiptScreen(
    modifier: Modifier = Modifier,
    viewModel: ReceiptViewModel = hiltViewModel(),
    configScreen: () -> Unit,
    navToTicketScreen: (id: Int) -> Unit
) {

    LaunchedEffect(Unit) { configScreen() }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val showLoading by viewModel.showLoading.collectAsState()

    val showDialogSave by viewModel.showDialogSave.collectAsState()

    val receipt by viewModel.receipt.collectAsState()
    val listDetailReceipt by viewModel.listDetailReceipt.collectAsState()
    val totalReceipt by viewModel.total.collectAsState()
    val idReceiptToNavigate by viewModel.idReceiptToNavigate.collectAsState()

    val isOpenMenuPaymentMethod by viewModel.isOpenMenuPaymentMethod.collectAsState()

    val listFocus = remember { mutableStateListOf(FocusRequester()) }

    val listPaymentMethod = stringArrayResource(R.array.payment_methods).toList()

    Log.d(
        "ReceiptScreen", """
        receipt: $receipt
        listDet: $listDetailReceipt
        total: $totalReceipt
        
    """.trimIndent()
    )

    ReceiptScreen(
        modifier = modifier,
        receipt = receipt,
        listDetailReceipt = listDetailReceipt,
        listPaymentMethod = listPaymentMethod,
        listState = listState,
        listFocus = listFocus,
        isOpenMenuPaymentMethod = isOpenMenuPaymentMethod,
        idReceiptToNavigate = idReceiptToNavigate,
        totalReceipt = totalReceipt,
        showDialogSave = showDialogSave,
        dialogSaveDismiss = { viewModel.setShowDialogSave(false) },
        dialogSaveConfirmButton = {
            viewModel.setShowDialogSave(false)
            viewModel.saveReceiptWithListDet(receipt, listDetailReceipt)
        },
        onDismissMenuPaymentMethod = { viewModel.setIsOpenMenuPaymentMethod(false) },
        onActionInteraction = { viewModel.setIsOpenMenuPaymentMethod(true) },
        onSelectedItemMenuPaymentMethod = {
            viewModel.setOnPaymentMethodChanged(it)
            viewModel.setIsOpenMenuPaymentMethod(false)
        },
        onActionDisposableEffect = {
            navToTicketScreen(idReceiptToNavigate)
            viewModel.resetNavTo()
        },
        onClickItemIconDelete = { position ->
            listFocus.remove(listFocus[position])
            viewModel.deleteDetailReceipt(position)
        },
        onValueChangeTitle = viewModel::onValueChangeTitle,
        onValueChangeNameItem = viewModel::setOnNameItemChanged,
        onValueChangeAmountItem = viewModel::setOnAmountItemChanged,
        onValueChangePriceItem = viewModel::setOnPriceItemChanged,
        onValueChangeTotalItem = viewModel::setOnTotalItemChanged,
        onLockedItemChanged = viewModel::setOnLockedItemChanged,
        onClickFAB = {
            listFocus.add(FocusRequester())
            viewModel.addDetailReceipt()
            scope.launch {
                val lastIndex = listDetailReceipt.size - 1
                listState.animateScrollToItem(lastIndex)
                listFocus.last().requestFocus()
            }
        },
        onClickSave = viewModel::onClickButtonSaveReceipt,
        showLoading = showLoading
    )
}

@Composable
fun ReceiptScreen(
    modifier: Modifier = Modifier,
    receipt: Receipt,
    listDetailReceipt: List<DetailReceipt>,
    listPaymentMethod: List<String>,
    listState: LazyListState,
    listFocus: SnapshotStateList<FocusRequester>,
    idReceiptToNavigate: Int,
    showLoading: Boolean,
    showDialogSave: Boolean,
    isOpenMenuPaymentMethod: Boolean,
    totalReceipt: Double,
    dialogSaveDismiss: () -> Unit,
    dialogSaveConfirmButton: () -> Unit,
    onDismissMenuPaymentMethod: () -> Unit,
    onActionInteraction: () -> Unit,
    onSelectedItemMenuPaymentMethod: (String) -> Unit,
    onActionDisposableEffect: () -> Unit,
    onClickItemIconDelete: (position: Int) -> Unit,
    onValueChangeTitle: (String) -> Unit,
    onValueChangeNameItem: (String, Int) -> Unit,
    onValueChangeAmountItem: (String, Int) -> Unit,
    onValueChangePriceItem: (String, Int) -> Unit,
    onValueChangeTotalItem: (String, Int) -> Unit,
    onLockedItemChanged: (Int) -> Unit,
    onClickFAB: () -> Unit,
    onClickSave: (receipt: Receipt, listDetailReceipt: List<DetailReceipt>) -> Unit,
) {

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (title, listDet, fab, pm, total, btnSave, div) = createRefs()

        ExtendedFloatingActionButton(
            modifier = Modifier.constrainAs(btnSave) {
                bottom.linkTo(parent.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
            },
            icon = { Icon(imageVector = Icons.Outlined.Check, contentDescription = null) },
            text = { Text(text = stringResource(id = R.string.save)) },
            onClick = { onClickSave(receipt, listDetailReceipt) })

        OutlinedTextField(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 8.dp)
                    width = Dimension.value(240.dp)
                },
            singleLine = true,
            value = receipt.title,
            onValueChange = onValueChangeTitle,
            label = {
                Text(
                    text = stringResource(id = R.string.label_title_receipt),
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        )

        Column(modifier = Modifier.constrainAs(pm) {
            top.linkTo(title.bottom, margin = 8.dp)
            start.linkTo(parent.start, margin = 8.dp)
            width = Dimension.value(200.dp)
        }) {
            OutlinedTextField(
                value = receipt.paymentMethod,
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                label = {
                    Text(
                        text = stringResource(id = R.string.label_title_payment_method),
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                interactionSource = remember {
                    MutableInteractionSource()
                }.also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect { interaction ->
                            if (interaction is PressInteraction.Release) onActionInteraction()
                        }
                    }
                }
            )

            SimpleMenu(
                modifier = Modifier.width(200.dp),
                expended = isOpenMenuPaymentMethod,
                onDismiss = onDismissMenuPaymentMethod,
                list = listPaymentMethod,
                onClick = onSelectedItemMenuPaymentMethod
            )
        }

        Text(
            modifier = Modifier.constrainAs(total) {
                bottom.linkTo(div.top, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
            },
            text = stringResource(
                id = uiR.string.text_total,
                totalReceipt
            ),
            fontSize = 24.sp
        )

        HorizontalDivider(modifier = Modifier.constrainAs(div) {
            top.linkTo(pm.bottom, margin = 8.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        LazyColumn(
            state = listState,
            modifier = Modifier.constrainAs(listDet) {
                top.linkTo(pm.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                bottom.linkTo(fab.top, margin = 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            itemsIndexed(listDetailReceipt) { position, det ->
                DetailReceiptCard(
                    position = position + 1,
                    focusRequester = listFocus[position],
                    det = det,
                    onValueChangeName = { onValueChangeNameItem(it, position) },
                    onValueChangeAmount = { onValueChangeAmountItem(it, position) },
                    onValueChangePrice = { onValueChangePriceItem(it, position) },
                    onValueChangeTotal = { onValueChangeTotalItem(it, position) },
                    onClickIconLock = { onLockedItemChanged(position) },
                    onClickIconDelete = { onClickItemIconDelete(position) }
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier.constrainAs(fab) {
                bottom.linkTo(parent.bottom, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
            },
            onClick = onClickFAB
        ) {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
        }
    }

    AnimatedVisibility(visible = showLoading) {
        SimpleLoading()
    }

    AnimatedVisibility(visible = showDialogSave) {
        AlertDialog(
            title = {
                Text(text = stringResource(R.string.title_dialog_save_receipt))
            },
            text = {
                Text(text = stringResource(R.string.desc_dialog_save_receipt))
            },
            confirmButton = {
                TextButton(
                    onClick = dialogSaveConfirmButton,
                    content = { Text(text = stringResource(dR.string.accept)) }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = dialogSaveDismiss,
                    content = { Text(text = stringResource(dR.string.cancel)) }
                )
            },
            onDismissRequest = dialogSaveDismiss
        )
    }

    DisposableEffect(idReceiptToNavigate) {
        if (idReceiptToNavigate != -1) onActionDisposableEffect()
        onDispose { }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewReceiptScreen(
) {
    SacaLaCuentaTheme {
        ReceiptScreen(
            receipt = Receipt(),
            listDetailReceipt = listOf(),
            listPaymentMethod = listOf(),
            listState = rememberLazyListState(),
            listFocus = remember { mutableStateListOf(FocusRequester()) },
            idReceiptToNavigate = -1,
            showLoading = false,
            isOpenMenuPaymentMethod = false,
            totalReceipt = 0.00,
            onDismissMenuPaymentMethod = {},
            onActionInteraction = {},
            onSelectedItemMenuPaymentMethod = {},
            onActionDisposableEffect = {},
            onClickItemIconDelete = {},
            onValueChangeNameItem = { _, _ -> },
            onValueChangeAmountItem = { _, _ -> },
            onValueChangePriceItem = { _, _ -> },
            onValueChangeTotalItem = { _, _ -> },
            onLockedItemChanged = {},
            onClickFAB = {},
            onClickSave = { _, _ -> },
            onValueChangeTitle = { _ -> },
            showDialogSave = false,
            dialogSaveDismiss = {},
            dialogSaveConfirmButton = {}
        )
    }
}