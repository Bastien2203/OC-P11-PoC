
import { test, expect } from '@playwright/test';

test('User can view speciality groups fetched from backend', async ({ page }) => {
  await page.goto('/');

  await expect(page.locator('h1')).toHaveText('Medhead');

  await expect(page.getByText('Chargement des spécialités...')).toBeHidden();

  await expect(page.getByText('Endodontie')).toBeVisible();
});