<template>
    <div class="min-h-screen bg-base-200">
        <header class="navbar bg-base-100 shadow-sm sticky top-0 z-50">
            <div class="flex-1 px-2 mx-2">
                <img src="../../assets/medhead.png" alt="medhead" class="h-14 w-auto" />
                <h1 class="sr-only">Medhead - Réservation de lits d'urgence</h1>
            </div>
        </header>

        <main class="flex flex-col items-center p-4 sm:p-8">
            <slot />
        </main>
    </div>
</template>