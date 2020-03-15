package dk.bluebox.demo.githubviewer.common

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

@FlowPreview
fun <T> mergeFlows(vararg flows: Flow<T>): Flow<T> = flowOf(*flows).flattenMerge()

@FlowPreview
fun <T> concatFlows(vararg flows: Flow<T>): Flow<T> = flow { flows.forEach { emit(it) } }.flattenConcat()

fun <T> Flow<T>.firstFlow(): Flow<T> = flow { emit(first()) }