# Branching Strategy - GitFlow

## Overview
We use GitFlow to manage our code releases and ensure production stability.

## Branch Types

### Main Branches
- **main**: Production-ready code only. Every commit here should be deployable.
- **develop**: Integration branch where features come together for testing.

### Supporting Branches
- **feature/**: New features and non-emergency bug fixes
  - Branch from: `develop`
  - Merge to: `develop`
  - Naming: `feature/description-of-feature`

- **release/**: Prepare new production releases
  - Branch from: `develop`  
  - Merge to: `main` and `develop`
  - Naming: `release/version-number`

- **hotfix/**: Emergency fixes for production
  - Branch from: `main`
  - Merge to: `main` and `develop`
  - Naming: `hotfix/critical-issue-description`

## Workflow Process

### For New Features:
1. Create feature branch from `develop`
2. Develop and test your feature
3. Create Pull Request to `develop`
4. Code review and approval required
5. Merge to `develop` and delete feature branch

### For Releases:
1. Create release branch from `develop`
2. Final testing and bug fixes
3. Merge to `main` with version tag
4. Merge back to `develop`
5. Deploy from `main`

## Protection Rules
- `main` and `develop` branches are protected
- All changes must go through Pull Requests
- CI/CD pipeline must pass before merge
- Minimum one reviewer approval required