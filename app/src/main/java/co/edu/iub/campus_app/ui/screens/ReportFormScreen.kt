package co.edu.iub.campus_app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.edu.iub.campus_app.domain.model.Service
import co.edu.iub.campus_app.domain.model.enums.ReportCategory
import co.edu.iub.campus_app.ui.components.CategoryBadge
import co.edu.iub.campus_app.ui.components.ErrorState
import co.edu.iub.campus_app.ui.components.FullScreenLoading
import co.edu.iub.campus_app.ui.components.InlineError
import co.edu.iub.campus_app.ui.theme.CampusBorder
import co.edu.iub.campus_app.ui.theme.CampusCanvas
import co.edu.iub.campus_app.ui.theme.CampusInk
import co.edu.iub.campus_app.ui.theme.CampusInkMuted
import co.edu.iub.campus_app.ui.theme.CampusRed
import co.edu.iub.campus_app.ui.theme.CampusSurface
import co.edu.iub.campus_app.ui.theme.CampusSurfaceMuted
import co.edu.iub.campus_app.ui.viewModel.ReportFormEvent
import co.edu.iub.campus_app.ui.viewModel.ReportFormViewModel
import co.edu.iub.campus_app.ui.state.ReportFormUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportFormScreen(
    onBack: () -> Unit,
    onReportCreated: (Long) -> Unit,
    viewModel: ReportFormViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ReportFormEvent.ReportCreated -> onReportCreated(event.reportId)
            }
        }
    }

    Scaffold(
        containerColor = CampusCanvas,
        topBar = {
            TopAppBar(
                title = { Text("Reportar novedad") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CampusCanvas,
                    titleContentColor = CampusInk,
                    navigationIconContentColor = CampusInk
                )
            )
        }
    ) { innerPadding ->
        when {
            state.loadingService -> {
                FullScreenLoading(modifier = Modifier.padding(innerPadding))
            }

            state.serviceError != null -> {
                ErrorState(
                    message = state.serviceError ?: "",
                    onRetry = viewModel::loadService,
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 20.dp)
                )
            }

            state.service != null -> {
                ReportFormContent(
                    state = state,
                    service = state.service!!,
                    onCategoryChange = viewModel::onCategoryChange,
                    onDescriptionChange = viewModel::onDescriptionChange,
                    onSubmit = viewModel::submit,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun ReportFormContent(
    state: ReportFormUiState,
    service: Service,
    onCategoryChange: (ReportCategory) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        ServiceContextCard(service = service)

        CategorySelector(
            state = state,
            onCategoryChange = onCategoryChange
        )

        DescriptionField(
            state = state,
            onDescriptionChange = onDescriptionChange
        )

        PrivacyNote()

        if (state.submitError != null) {
            InlineError(message = state.submitError)
        }

        Button(
            onClick = onSubmit,
            enabled = !state.submitting,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("submit_report"),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (state.submitting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(if (state.submitting) "Enviando..." else "Enviar novedad")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ServiceContextCard(service: Service) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = CampusSurface,
        border = BorderStroke(1.dp, CampusBorder)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryBadge(category = service.category)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = service.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = CampusInk
                )
                Text(
                    text = service.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = CampusInkMuted
                )
            }
        }
    }
}

@Composable
private fun CategorySelector(
    state: ReportFormUiState,
    onCategoryChange: (ReportCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Categoría",
            style = MaterialTheme.typography.labelLarge,
            color = CampusInk
        )

        Box {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("category_selector")
                    .clickable { expanded = true },
                shape = RoundedCornerShape(8.dp),
                color = CampusSurface,
                border = BorderStroke(
                    1.dp,
                    if (state.categoryError != null) CampusRed else CampusBorder
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.category?.label ?: "Selecciona una categoría",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (state.category == null) CampusInkMuted else CampusInk,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = null,
                        tint = CampusInkMuted
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                ReportCategory.entries.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.label) },
                        onClick = {
                            onCategoryChange(category)
                            expanded = false
                        }
                    )
                }
            }
        }

        if (state.categoryError != null) {
            Text(
                text = state.categoryError,
                style = MaterialTheme.typography.labelSmall,
                color = CampusRed
            )
        }
    }
}

@Composable
private fun DescriptionField(
    state: ReportFormUiState,
    onDescriptionChange: (String) -> Unit
) {
    OutlinedTextField(
        value = state.description,
        onValueChange = onDescriptionChange,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("report_description"),
        label = { Text("Descripción") },
        placeholder = { Text("Describe qué encontraste") },
        minLines = 5,
        maxLines = 8,
        isError = state.descriptionError != null,
        supportingText = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = state.descriptionError ?: "Mínimo 10 caracteres",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (state.descriptionError != null) CampusRed else CampusInkMuted
                )
                Text(
                    text = "${state.description.length}/500",
                    style = MaterialTheme.typography.labelSmall,
                    color = CampusInkMuted
                )
            }
        },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CampusSurface,
            unfocusedContainerColor = CampusSurface,
            focusedBorderColor = CampusInk,
            unfocusedBorderColor = CampusBorder,
            errorBorderColor = CampusRed,
            cursorColor = CampusInk,
            focusedTextColor = CampusInk,
            unfocusedTextColor = CampusInk
        )
    )
}

@Composable
private fun PrivacyNote() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = CampusSurfaceMuted
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = CampusInkMuted,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "No incluyas nombres, teléfonos ni datos personales.",
                style = MaterialTheme.typography.bodySmall,
                color = CampusInkMuted
            )
        }
    }
}
