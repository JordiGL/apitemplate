package com.golojodev.apitemplate.domain.usecases

interface UseCaseProvider {
    val onSaveTheme: SaveThemeUseCase
    val onGetTheme: GetThemeUseCase
    val onGetModels: GetModelsUseCase
    val onFetchModels: FetchModelsUseCase
    val onUpdateModel: UpdateModelUseCase
    val onGetFavorites: GetFavoritesUseCase
}