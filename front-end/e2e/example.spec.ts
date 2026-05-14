
import { test, expect } from '@playwright/test';

test('User can view speciality groups fetched from backend', async ({ page }) => {
  await page.goto('/');

  await expect(page.getByText('Chargement des spécialités...')).toBeHidden();

  await expect(page.getByText('Anesthésie')).toBeVisible();
});


test('User can choose a speciality, enter its address, and book a bed in an available hospital', async ({ page }) => {
  await page.goto('http://localhost:5173/');
  await page.locator('a').filter({ hasText: 'Groupe de médecine générale' }).click();
  await page.locator('a').filter({ hasText: 'Cardiologie' }).click();
  await page.getByRole('button', { name: 'Suivant' }).click();
  await page.getByRole('textbox', { name: 'Commencez à taper une adresse' }).click();
  await page.getByRole('textbox', { name: 'Commencez à taper une adresse' }).fill('17 Rue paul émile victor la rochelle');
  await page.getByText('Rue Paul-Émile Victor 17000 La Rochelle17, Charente-Maritime, Nouvelle-Aquitaine').click();
  await page.getByRole('button', { name: 'Rechercher' }).click();
  await expect(page.getByRole('main')).toContainText('2');
  await expect(page.locator('h2')).toContainText('Hôpital trouvé !');
  await page.getByRole('button', { name: 'Réserver un lit' }).click();
  await expect(page.locator('h2')).toContainText('Réservation confirmée');
  await expect(page.getByRole('strong')).toContainText('Hôpital Fred Brooks');
  await expect(page.getByRole('button')).toContainText('Retour à l\'accueil');
});


test('User choose an anavailable speciality, so a message indicate that no hospital found with this speciality', async ({ page }) => {
  await page.goto('http://localhost:5173/');
  await page.locator('a').filter({ hasText: 'Oncologie clinique' }).click();
  await page.locator('a').click();
  await page.getByRole('button', { name: 'Suivant' }).click();
  await page.getByRole('textbox', { name: 'Commencez à taper une adresse' }).click();
  await page.getByRole('textbox', { name: 'Commencez à taper une adresse' }).fill('17 Rue paul émile victor la rochelle');
  await page.getByText('Rue Paul-Émile Victor 17000 La Rochelle17, Charente-Maritime, Nouvelle-Aquitaine').click();
  await page.getByRole('button', { name: 'Rechercher' }).click();
  await expect(page.locator('h2')).toContainText('Aucun établissement');
  await expect(page.getByRole('button')).toContainText('Nouvelle recherche');
});